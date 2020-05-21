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
 * @author 高谦
 * 学生选课子系统的关于页面模板初始化的controller类。
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
     * 展示选课界面，这里默认的查询所有 本专业，本学年 的课程信息。
     * @param request  HttpServletRequest 参数
     * @param parmMap  发给前端的参数集合
     * @return
     */
    @GetMapping("/selectcourse")
    public String selectCourse(Map<String,Object> parmMap, HttpServletRequest request){

        Boolean canSelect=powerService.getSelectCourse();
        if(!canSelect){
            parmMap.put("reason","当前不是选课时间，如有需要请联系管理员！");
            parmMap.put("url","/index");
            return "toindex";
        }
        Integer semesterId=semesterService.getCurrentSemesterId();
        HttpSession session =request.getSession();
        User user=(User) session.getAttribute("user");
        Integer sno=user.getAccount();
        Integer majorId=user.getMajorid();
        List<Semester> semesters=semesterService.getSemesterEntity();
        ArrayList<SelectCourse> selectedList=
                (ArrayList<SelectCourse>) selectCourseService.getSelectedCourseList(sno,semesterId);
        ArrayList<SelectCourseView> courseViews=
                (ArrayList<SelectCourseView>)
                        selectCourseService.getAllCourseList(semesterId,majorId);
        ArrayList<College> colleges=(ArrayList<College>) collegeService.getAllCollege().getData();
        parmMap.put("courseselectedlist",selectedList);
        parmMap.put("allcourses",courseViews);
        parmMap.put("semesterlist",semesters);
        parmMap.put("colleges",colleges);
        return "selectcourse";
    }

    /**
     * 学生课表查询界面的初始化controller 接口
     * @param parMap 参数列表
     * @param request  servlet request对象
     * @return  返回初始化的界面
     */
    @GetMapping("/coursetable")
    public String getCourseTable(Map<String,Object> parMap,HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        Integer sno=user.getAccount();
        Integer semesterId=semesterService.getCurrentSemesterId();
        ArrayList<SelectCourseView> courseTable=(ArrayList<SelectCourseView>)
                selectCourseService.getCourseTable(semesterId,sno);
        List<Semester> semesters=semesterService.getSemesterEntity();
        Semester semester=semesterService.getCurrentSemesterInfo();
        parMap.put("coursetable",courseTable);
        parMap.put("semesterlist",semesters);
        parMap.put("currentSemester",semester);
        return "coursetable";
    }

    /**
     * 学生手机端选课界面的接口controller
     * @param parmMap  传递到前端的参数集合
     * @return
     */


    @GetMapping("/selectcoursemobile")
    public String selectcoursemobile(Map<String, Object> parmMap){
        Boolean canSelect=powerService.getSelectCourse();
        if(!canSelect){
            parmMap.put("reason","当前不是选课时间，如有需要请联系管理员！");
            parmMap.put("url","/index");
            return "toindex";
        }
        ResponseMessage res=collegeService.getAllCollege();
        List<College> collegeList=(List<College>) res.getData();
        parmMap.put("collegelist",collegeList);
        return "selectcoursemobile";
    }

    /**
     * 学生手机端课程表的展示界面。
     * @param parmMap  传递到前端的参数。
     * @return
     */
    @GetMapping("/coursetablemobile")
    public String coursetablemobile(Map<String, Object> parmMap){
        List<Semester> semesters=semesterService.getSemesterEntity();
        Semester semester=semesterService.getCurrentSemesterInfo();
        parmMap.put("semesterlist",semesters);
        parmMap.put("currentSemester",semester);
        return "coursetablemobile";
    }
}
