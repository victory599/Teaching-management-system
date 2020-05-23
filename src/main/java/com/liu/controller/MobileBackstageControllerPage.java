package com.liu.controller;

import com.liu.entity.Semester;
import com.liu.entity.Student;
import com.liu.entity.Teacher;
import com.liu.entity.User;
import com.liu.service.PowerService;
import com.liu.service.SemesterService;
import com.liu.service.StudentService;
import com.liu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class MobileBackstageControllerPage {
    @Autowired
    PowerService powerService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    SemesterService semesterService;

    /**
     * 检查权限，是否管理员
     * @param request
     * @return
     */
    public boolean checkPower(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return false;
        }
        return user.getType() == 2;
    }

    @RequestMapping("/GoMobileHomePage")
    public String goMobileHomePage(HttpServletRequest request, Map map) {
        if (checkPower(request) == false) {
            return "error";
        }
        User user = (User) request.getSession().getAttribute("user");
        map.put("name", user.getTname());

        Semester currentSemesterInfo = semesterService.getCurrentSemesterInfo();
        map.put("start", currentSemesterInfo.getStart());
        map.put("end", currentSemesterInfo.getEnd());
        map.put("semester", currentSemesterInfo.getSemester());

        return "mobileHomePage";
    }

    @RequestMapping("/GoPowerInfo")
    public String goPowInfo(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }

        request.setAttribute("abnormal", powerService.getAbnormal());
        request.setAttribute("score", powerService.getScore());
        request.setAttribute("selectCourse", powerService.getSelectCourse());
        return "powInfo";
    }

    @RequestMapping("/GoStudentInfo")
    public String goStudentInfo(HttpServletRequest request, Map<String, Object> map) {
        if (checkPower(request) == false) {
            return "error";
        }
        List<Student> allStudent = studentService.getAllStudent();
        map.put("allStudent", allStudent);

        return "stuInfo";
    }

    @RequestMapping("/mStudentPage")
    public String studentPage(Map<String, Object> map, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        List<Student> allStudent = (List<Student>) request.getSession().getAttribute("allStudent");
        map.put("allStudent", allStudent);

        return "stuInfo";
    }

    @RequestMapping("/GoTeachearInfo")
    public String goTeacherInfo(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        List<Teacher> allTeacher = teacherService.getAllTeacher();
        request.getSession().setAttribute("allTeacher", allTeacher);
        return "forward:/mTeacherPage";
    }

    @RequestMapping("/mTeacherPage")
    public String teacherPage(Map<String, Object> map, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        List<Teacher> allTeacher = (List<Teacher>) request.getSession().getAttribute("allTeacher");
        map.put("allTeacher", allTeacher);
        request.setAttribute("allTeacher", allTeacher);

        return "teaInfo";
    }

    @RequestMapping("/teacherSearch")
    public String teacherSearch(Map<String, Object> map, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        return "teaSearch";
    }

    @RequestMapping("/studentSearch")
    public String studentSearch(Map<String, Object> map, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        return "stuSearch";
    }

    @RequestMapping("/mobileSearchStudent")
    public String mobileSearchStudent(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        String snoString = request.getParameter("sno");
        Integer sno = null;
        try {
            sno = Integer.parseInt(snoString);
        } catch (Exception e) {
            return "error";
        }
        Student student = new Student();
        student.setSno(sno);
        List<Student> studentsByExample = studentService.getStudentsByExample(student);
        request.getSession().setAttribute("allStudent", studentsByExample);
        return "forward:/mStudentPage";
    }

    @RequestMapping("/mobileSearchTeacher")
    public String mobileSearchTeacher(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        String tname = request.getParameter("tname");
        Teacher teacher = new Teacher();
        teacher.setTname(tname);
        List<Teacher> teacherByExample = teacherService.getTeacherByExample(teacher);
        request.getSession().setAttribute("allTeacher", teacherByExample);
        return "forward:/mTeacherPage";
    }

    @RequestMapping("/addSemester")
    public String addSemester(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String semester = request.getParameter("semester");
        String id = request.getParameter("id");
        Integer semesterId = null;
        try {
            semesterId = Integer.parseInt(id);
        } catch (Exception e) {
            return "error";
        }
        semesterService.addSemester(new Semester(semesterId, start, end, semester));

        return "forward:/GoMobileHomePage";
    }

    @RequestMapping("/GoSemesterInfo")
    public String goSemesterInfo(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        return "couManage";
    }

    @RequestMapping("/OpenSelectCourse")
    public String openSelectCourse(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.openSelectCourse();
        return "forward:/GoPowerInfo";
    }

    @RequestMapping("/CloseSelectCourse")
    public String closeSelectCourse(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.closeSelectCourse();
        return "forward:/GoPowerInfo";
    }

    @RequestMapping("/OpenScore")
    public String openScore(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.openScore();
        return "forward:/GoPowerInfo";
    }

    @RequestMapping("/CloseScore")
    public String closeScore(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.closeScore();
        return "forward:/GoPowerInfo";
    }

    @RequestMapping("/OpenAbnormal")
    public String openAbnormal(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.openAbnormal();
        return "forward:/GoPowerInfo";
    }

    @RequestMapping("/CloseAbnormal")
    public String closeAbnormal(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.closeAbnormal();
        return "forward:/GoPowerInfo";
    }
}
