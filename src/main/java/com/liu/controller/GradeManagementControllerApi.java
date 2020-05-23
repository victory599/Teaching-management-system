package com.liu.controller;

import com.liu.entity.Course;
import com.liu.entity.Semester;
import com.liu.entity.User;
import com.liu.service.CollegeService;
import com.liu.service.GradeManagementService;
import com.liu.service.SelectCourseService;
import com.liu.service.SemesterService;
import com.liu.service.PowerService;
import com.liu.service.CourseService;
import com.liu.utils.ResponseMessage;

import com.liu.vo.GradeManagementView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *  成绩管理子系统所有用到的 api 函数。
 */
@RestController
public class GradeManagementControllerApi {
    @Autowired
    GradeManagementService gradeManagementService;
    @Autowired
    CollegeService collegeService;
    @Autowired
    SelectCourseService selectCourseService;
    @Autowired
    SemesterService semesterService;
    @Autowired
    PowerService powerService;
    @Autowired
    CourseService courseService;

    // 传参...
    private Integer tmpcno;
    private Integer tmpSemesterId;

    /* 学生端*/
    /**
     * 学生某学期的成绩信息
     */
    @GetMapping(value = "/getcoursegrade")
    public ResponseMessage getCourseGrade(@RequestParam("semester") String semester,
                                          @RequestParam("start") String start, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();

        ArrayList<GradeManagementView> courseGradeTable = (ArrayList<GradeManagementView>) gradeManagementService.getCourseGrade(start, semester, sno);

        return new ResponseMessage(ResponseMessage.SUCCESS, "查询成功！", courseGradeTable);
    }

    /* 教师端*/
    /**
     * 获取学生的成绩信息
     */
    @GetMapping(value = "/getstudentgrade")
    public ResponseMessage getStudentGrade(@RequestParam("semester") String semester, @RequestParam("start") String start,
                                           @RequestParam("cno") Integer cno, @RequestParam("sno") Integer sno, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer tno = user.getAccount();

        ArrayList<GradeManagementView> studentGradeTable = (ArrayList<GradeManagementView>) gradeManagementService.getStudentsGrade(start, semester, cno, tno, sno);

        return new ResponseMessage(ResponseMessage.SUCCESS, "查询成功！", studentGradeTable);
    }


    /**
     * 老师更改某学期某课程某学生的成绩
     */
    @RequestMapping("/setstudentgradeById")
    public ResponseMessage setStudentGrade(@RequestParam("semester") String semester, @RequestParam("start") String start,
                                           @RequestParam("cno") String ccno, @RequestParam("sno") Integer sno,
                                           @RequestParam("detail") String detail, HttpServletRequest request) {
        Integer cno = Integer.valueOf(ccno);
        Semester s = semesterService.getSemesterByStartAndSemester(Integer.valueOf(start), Integer.valueOf(semester));
        Integer semesterId = s.getSemesterId();
        String percent = gradeManagementService.getCoursePercent(semesterId, cno);
        String[] tmp = percent.split(",");
        int p1 = Integer.parseInt(tmp[0]);
        int p2 = Integer.parseInt(tmp[1]);
        int p3 = Integer.parseInt(tmp[2]);
        String[] d = detail.split(",");
        int g1 = Integer.parseInt(d[0]);
        int g2 = Integer.parseInt(d[1]);
        int g3 = Integer.parseInt(d[2]);
        Integer totalScore = (p1 * g1 + p2 * g2 + p3 * g3) / 100;

        boolean power = powerService.getSelectCourse();
        if (power == false) {
            return new ResponseMessage(ResponseMessage.SUCCESS, "当前时段没有权限访问！", power);
        } else {
            gradeManagementService.setStudentGradeById(semesterId, cno, sno, detail, totalScore);
            return new ResponseMessage(ResponseMessage.SUCCESS, "修改成功！", detail);
        }
    }

    /**
     * 获取导入框返回的学年学期课程号
     */
    @GetMapping(value = "/getparams")
    @ResponseBody
    public String getParams(@RequestParam("semester") String semester, @RequestParam("start") String start, @RequestParam("cno") String ccno) {
        Semester s = semesterService.getSemesterByStartAndSemester(Integer.valueOf(start), Integer.valueOf(semester));
        Integer semesterId = s.getSemesterId();
        Integer cno = Integer.valueOf(ccno);
        tmpSemesterId = semesterId;     //暂存s
        tmpcno = cno;
        return "ok";
    }

    /**
     * 上传临时文件并导入，导入完毕后删除临时文件，文件格式为xls或xlsx
     */
    @RequestMapping("/import")
    @ResponseBody
    public String importExcel(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件前缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        final File file = File.createTempFile(getUUID(), prefix);
        // 转存文件
        multipartFile.transferTo(file);

        if (fileName == null && "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                Boolean flag = gradeManagementService.setStudentGradeInGroup(tmpSemesterId, tmpcno, file);

                // 导入结束时，删除临时文件
                deleteFile(file);
                if (flag) {
                    return "导入成功！";
                } else {
                    return "导入失败！";
                }
            }
            return "文件格式错误！";
        }
    }

    /**
     * 获取老师课程
     * @param semester
     * @param start
     * @param request
     * @return
     */
    @RequestMapping("/getteachercourse")
    @ResponseBody
    private ResponseMessage test(@RequestParam("semester") String semester, @RequestParam("start") String start, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer tno = user.getAccount();
        List<Course> teacherCourse = gradeManagementService.getCourseOfTeacher(start, semester, tno);

        return new ResponseMessage(ResponseMessage.SUCCESS, "查询成功！", teacherCourse);
    }

    /**
     * 获取32位UUID字符串 临时文件名
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 删除临时文件
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
