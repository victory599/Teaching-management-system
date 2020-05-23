package com.liu.controller;

import com.liu.entity.College;
import com.liu.entity.SelectCourse;
import com.liu.entity.Semester;

import com.liu.entity.User;
import com.liu.service.CollegeService;
import com.liu.service.PowerService;
import com.liu.service.SelectCourseService;
import com.liu.service.SemesterService;

import com.liu.utils.ResponseMessage;
import com.liu.vo.SelectCourseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

/**
 * 学生选课子系统的关于页面模板初始化的controller
 */
@Controller
public class SelectCourseControllerPage {
    @Autowired
    CollegeService collegeService;
    @Autowired
    SelectCourseService selectCourseService;
    @Autowired
    SemesterService semesterService;
    @Autowired
    PowerService powerService;

    /**
     * 展示选课界面，这里默认的查询所有 本专业，本学年的课程信息
     * @param request HttpServletRequest 参数
     * @param model 发给前端的参数集合
     * @return
     */
    @GetMapping("/selectcourse")
    public String selectCourse(Map<String, Object> model, HttpServletRequest request) {
        Boolean canSelect = powerService.getSelectCourse();
        if (!canSelect) {
            model.put("reason", "当前不是选课时间，如有需要请联系管理员！");
            model.put("url", "/index");
            return "toindex";
        }
        Integer semesterId = semesterService.getCurrentSemesterId();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();
        Integer majorId = user.getMajorid();
        List<Semester> semesters = semesterService.getSemesterEntity();
        ArrayList<SelectCourse> selectedList = (ArrayList<SelectCourse>) selectCourseService.getSelectedCourseList(sno, semesterId);
        ArrayList<SelectCourseView> courseViews = (ArrayList<SelectCourseView>)selectCourseService.getAllCourseList(semesterId, majorId);
        ArrayList<College> colleges = (ArrayList<College>) collegeService.getAllCollege().getData();

        model.put("courseselectedlist", selectedList);
        model.put("allcourses", courseViews);
        model.put("semesterlist", semesters);
        model.put("colleges", colleges);
        return "selectcourse";
    }

    /**
     * 学生课表查询界面的初始化controller 接口
     * @param model  参数列表
     * @param request servlet request对象
     * @return 返回初始化的界面
     */
    @GetMapping("/coursetable")
    public String getCourseTable(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();
        Integer semesterId = semesterService.getCurrentSemesterId();
        ArrayList<SelectCourseView> courseTable = (ArrayList<SelectCourseView>)selectCourseService.getCourseTable(semesterId, sno);
        List<Semester> semesters = semesterService.getSemesterEntity();
        Semester semester = semesterService.getCurrentSemesterInfo();

        model.put("coursetable", courseTable);
        model.put("semesterlist", semesters);
        model.put("currentSemester", semester);
        return "coursetable";
    }

    /**
     * 学生手机端选课界面的接口
     * @param model 传递到前端的参数集合
     * @return
     */
    @GetMapping("/selectcoursemobile")
    public String selectcoursemobile(Map<String, Object> model) {
        Boolean canSelect = powerService.getSelectCourse();
        if (!canSelect) {
            model.put("reason", "当前不是选课时间，如有需要请联系管理员！");
            model.put("url", "/index");
            return "toindex";
        }
        ResponseMessage res = collegeService.getAllCollege();
        List<College> collegeList = (List<College>) res.getData();
        model.put("collegelist", collegeList);
        return "selectcoursemobile";
    }

    /**
     * 学生手机端课程表的展示界面
     * @param model 传递到前端的参数。
     * @return
     */
    @GetMapping("/coursetablemobile")
    public String coursetablemobile(Map<String, Object> model) {
        List<Semester> semesters = semesterService.getSemesterEntity();
        Semester semester = semesterService.getCurrentSemesterInfo();

        model.put("semesterlist", semesters);
        model.put("currentSemester", semester);
        return "coursetablemobile";
    }
}