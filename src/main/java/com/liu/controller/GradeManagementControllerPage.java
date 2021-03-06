package com.liu.controller;

import com.liu.entity.Semester;
import com.liu.entity.User;
import com.liu.service.GradeManagementService;
import com.liu.service.PowerService;
import com.liu.service.SemesterService;
import com.liu.vo.GradeManagementView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import java.util.Map;

@Controller
public class GradeManagementControllerPage {
    @Autowired
    GradeManagementService gradeManagementService;
    @Autowired
    SemesterService semesterService;
    @Autowired
    PowerService powerService;

    // 学生端PC
    @RequestMapping ("/gradeinfo")
    public String gradeManagement(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();
        Semester semester = semesterService.getCurrentSemesterInfo();
        List<GradeManagementView> studentGradeList = gradeManagementService.getCourseGrade(semester.getStart(), semester.getSemester(), sno);
        model.put("gradetable", studentGradeList);
        model.put("currentSemester", semester);
        for (GradeManagementView temp : studentGradeList) {
            temp.getCname();
        }
        return "GradeManagementStudent";
    }

    // 学生端mobile
    @RequestMapping ("/gradeinfomobile")
    public String gradeManagementm(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();
        Semester semester = semesterService.getCurrentSemesterInfo();
        List<GradeManagementView> studentGradeList = gradeManagementService.getCourseGrade(semester.getStart(), semester.getSemester(), sno);
        model.put("gradetable", studentGradeList);
        model.put("currentSemester", semester);
        for (GradeManagementView temp : studentGradeList) {
            temp.getCname();
        }
        return "GradeStudentM";
    }

    // 教师端PC
    @RequestMapping ("/gradeinfoteacher")
    public String gradeManagementTeacher(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer tno = user.getAccount();
        boolean permission = powerService.getScore();//获取选课权限
        Semester semester = semesterService.getCurrentSemesterInfo();

        model.put("currentSemester", semester);
        model.put("permission", permission);

        return "GradeManagementTeacher";
    }

    // 教师端mobil
    @RequestMapping ("/gradeteachermobile")
    public String gradeManagementTeacherm(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer tno = user.getAccount();

        boolean permission = powerService.getScore();//获取选课权限
        Semester semester = semesterService.getCurrentSemesterInfo();

        model.put("currentSemester", semester);
        model.put("permission", permission);

        return "GradeTeacherM";
    }
}

