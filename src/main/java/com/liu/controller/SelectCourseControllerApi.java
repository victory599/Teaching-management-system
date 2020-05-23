package com.liu.controller;

import com.liu.entity.Scheduling;

import com.liu.entity.SelectCourse;
import com.liu.entity.Semester;
import com.liu.entity.User;
import com.liu.service.CollegeService;

import com.liu.service.ConflictService;

import com.liu.service.SelectCourseService;
import com.liu.service.SemesterService;
import com.liu.utils.ResponseMessage;
import com.liu.vo.SelectCourseView;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 选课子系统所有用到的 api 函数
 */
@RestController
public class SelectCourseControllerApi {
    @Autowired
    CollegeService collegeService;
    @Autowired
    SelectCourseService selectCourseService;
    @Autowired
    SemesterService semesterService;
    @Autowired
    ConflictService conflictService;

    /**
     * 根据条件查询课程的接口
     * @param college  开课学院
     * @param capacity 是否有余量
     * @param cno      课程号码
     * @param cname    课程名称
     * @param tname    教师姓名
     * @return
     */
    @PostMapping("/filtercourse")
    public ResponseMessage filterCourse(@RequestParam("college") String college,
                                        @RequestParam("capacity") String capacity,
                                        @RequestParam("cno") String cno,
                                        @RequestParam("cname") String cname,
                                        @RequestParam("tname") String tname,
                                        HttpServletRequest request) {
        Integer semesterId = semesterService.getCurrentSemesterId();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();
        Integer majorid = user.getMajorid();
        ArrayList<SelectCourseView> courseViews =
                (ArrayList<SelectCourseView>) selectCourseService.getCourseList(semesterId, majorid, college, capacity, cno, cname, tname);
        ArrayList<SelectCourse> selectedList =
                (ArrayList<SelectCourse>) selectCourseService.getSelectedCourseList(sno, semesterId);

        Object[] res = new Object[2];
        res[0] = courseViews;
        res[1] = selectedList;
        return new ResponseMessage(ResponseMessage.SUCCESS, "请求成功", res);
    }

    /**
     * 返回所有学院的 Controller 接口
     * @return
     */
    @GetMapping("/getcolleges")
    public ResponseMessage getCollege() {
        return collegeService.getAllCollege();
    }

    /**
     * 根据学院id 返回学院相关信息的接口
     * @param id 学院的id
     * @return 返回的是
     */
    @PostMapping("/getcollegebyid")
    public ResponseMessage getCollegeById(@RequestParam String id) {
        return collegeService.getCollegeById(id);
    }

    /**
     * 选定一个课程，根据课程的课程号和学生的学号将信息加入到选课表中
     * @param cno     课程号
     * @param request 用于获取学号，注意，如果不登录直接请求接口，会报错！postman检测不了这个接口。
     * @return 返回一个ResponseMessage ，标注是否添加成功！
     */
    @PostMapping("/setcourseselected")
    public ResponseMessage addCourseSelected(@RequestParam("cno") Integer cno, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();
        Integer semesterId = semesterService.getCurrentSemesterId();
        Scheduling courseSchedulig = selectCourseService.getCourseInfoWithCondition(semesterId, cno);
        String timeStr = courseSchedulig.getCourseTime();
        Boolean flag = conflictService.student(semesterId, sno, timeStr);
        if (!flag) {
            return new ResponseMessage(ResponseMessage.TIME_CONFLICT, "选课时间冲突", null);
        }

        SelectCourse selectCourse = selectCourseService.addCourseToTable(semesterId, sno, cno);
        if (selectCourse == null) {
            return new ResponseMessage(ResponseMessage.INSERT_EXCEPTION, "插入失败", null);
        } else {
            return new ResponseMessage(ResponseMessage.SUCCESS, "插入成功！", selectCourse);
        }
    }

    /**
     * 取消一门选课
     * @param cno     取消的课程号
     * @param request 用于获取学号，注意，如果不登录直接请求接口，会报错！postman检测不了这个接口。
     * @return
     */
    @PostMapping("/canclecourse")
    public ResponseMessage cancleCourse(@RequestParam("cno") Integer cno, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();

        Integer semesterId = semesterService.getCurrentSemesterId();
        SelectCourse selectCourse = selectCourseService.removeCourse(semesterId, cno, sno);
        if (selectCourse == null) {
            return new ResponseMessage(ResponseMessage.DELETE_EXCEPTION, "删除失败", null);
        } else {
            return new ResponseMessage(ResponseMessage.SUCCESS, "操作成功", selectCourse);
        }
    }

    /**
     * 生成学生的选课表信息。
     * @param start    学年开始的年
     * @param semester 某个学年的学期
     * @param request  用于获取学号，注意，如果不登录直接请求接口，会报错！postman检测不了这个接口。
     * @return 这个学期的课表
     */
    @GetMapping("/getcoursetable")
    public ResponseMessage getCourseTable(@RequestParam("semester") Integer semester, @RequestParam("start") Integer start, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();
        Semester temp = semesterService.getSemesterByStartAndSemester(start, semester);
        if (temp == null) {
            // 当前没有这个学期
            return new ResponseMessage(ResponseMessage.semesterNotFound, "当前没有这个学期", null);
        }
        ArrayList<SelectCourseView> courseTable = (ArrayList<SelectCourseView>)selectCourseService.getCourseTable(temp.getSemesterId(), sno);
        return new ResponseMessage(ResponseMessage.SUCCESS, "查询成功！", courseTable);
    }

    /**
     * 获取所有学期的api 接口。
     * @return
     */
    @GetMapping("/getsemesterlist")
    public ResponseMessage getsemesterlist() {
        List<Semester> semesters = semesterService.getSemesterEntity();
        return new ResponseMessage(200, "全部的信息", semesters);
    }
}