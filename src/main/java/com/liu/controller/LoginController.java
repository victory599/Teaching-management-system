package com.liu.controller;

import com.liu.entity.Student;
import com.liu.entity.Teacher;
import com.liu.entity.User;
import com.liu.service.*;
import com.liu.utils.UserAgentParser;
import com.liu.vo.SelectCourseView;
import com.liu.vo.StudentGradeIndexView;

import com.liu.vo.TeaCourseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import java.util.Map;

/**
 * 登录登出功能实现，注意：登录界面不要设置拦截
 */
@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    SelectCourseService selectCourseService;
    @Autowired
    SemesterService semesterService;
    @Autowired
    SchedulingService schedulingService;
    @Autowired
    CollegeService collegeService;

    /**
     * 首页，如果没有登录则跳转到登录页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, Map<String, Object> model) {
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("user");
        Integer semesterId = semesterService.getCurrentSemesterId();

        // 判断当前访问用户的使用平台：pc端还是Mobil端
        String userAgent = request.getHeader("User-Agent");
        UserAgentParser userAgentParser = new UserAgentParser(userAgent);
        String platform = userAgentParser.getPlatform();

        // 判断是否已登录
        if (userInfo == null) {
            // 没有登录，跳转登录页面
            if (platform.equals("mobile"))
                return "MobileLogin";
            return "login";
        } else {
            // 已经登录，判断用户类别：0表示学生，1表示教师，2表示管理员
            User user = (User) userInfo;
            Integer type = user.getType();
            model.put("userinfo", user);
            if (type == 0) {
                List<SelectCourseView> courseTable = selectCourseService.getCourseTable(semesterId, user.getAccount());
                List<StudentGradeIndexView> gradeList = selectCourseService.getGrade(semesterId, user.getAccount());
                model.put("courseTable", courseTable);
                model.put("gradeList", gradeList);

                if (platform.equals("mobile"))
                    return "msh";

                return "student";
            }else if (type == 1) {
                int tno = user.getAccount();
                List<TeaCourseView> teaCourseViews = schedulingService.getCourseInfoByTno(tno);
                session.setAttribute("CourseTable",teaCourseViews);
                model.put("courseTable", teaCourseViews);   //课表

                Teacher teacher = teacherService.getTeacherByTno(tno);
                model.put("teainfo", teacher);

                int cid = teacher.getCollegeId();
                String colname = collegeService.getColnameById(cid);
                model.put("colname", colname);

                if (platform.equals("mobile"))
                    return "MobileTeacherHome";
                return "teacher";
            }else if(type == 2){
                if (platform.equals("mobile")) {
                    return "redirect:/GoMobileHomePage";
                } else {
                    return "redirect:/GoHomePage";
                }
            }
            return "login";
        }
    }

    /**
     * 处理登录请求: 验证账号、查询数据等
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public String login(HttpServletRequest request, Map<String, Object> model,
                        @RequestParam("account") String account, @RequestParam("password") String password) {
        // 判断当前访问用户的使用平台：pc端还是Mobil端
        String useragent = request.getHeader("User-Agent");
        UserAgentParser userAgentParser = new UserAgentParser(useragent);
        String platform = userAgentParser.getPlatform();

        HttpSession session = request.getSession();
        User user = userService.LoginFun(account, password);
        if(user == null){
            model.put("error_msg", "用户名或者密码错误，请重新输入!");
            if (platform.equals("mobile"))
                return "MobileLogin";
            return "login";
        } else {
            // 判断用户类别，并保存信息到session中
            boolean error = false;
            if (user.getType() == 0) {
                // 学生
                Student student = studentService.getStudentBySno(user.getAccount());
                if (student != null) {
                    user.setMajor(student.getMajor());
                    user.setSname(student.getSname());
                    user.setMajorid(student.getMajorId());
                    user.setCollege(student.getCollege());
                    user.setKlass(student.getKlass());
                } else {
                    error = true;
                }
            } else if (user.getType() == 1) {
                // 老师
                Teacher teacher = teacherService.getTeacherByTno(user.getAccount());
                if (teacher != null) {
                    user.setTname(teacher.getTname());
                } else {
                    error = true;
                }
            } else if (user.getType() == 2) {
                // 管理员
                Teacher teacher = teacherService.getTeacherByTno(user.getAccount());
                if (teacher != null) {
                    user.setTname(teacher.getTname());
                } else {
                    error = true;
                }
            }
            if (error) {
                model.put("error_msg", "数据库中找不到您的详细信息，请联系管理员！");
                if (platform.equals("mobile"))
                    return "MobileLogin";
                return "login";
            } else {
                session.setAttribute("user", user);
                return "redirect:/index";
            }
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        return "redirect:/index";
    }
}