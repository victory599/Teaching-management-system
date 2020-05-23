package com.liu.controller;

import com.liu.dao.SchedulingDao;
import com.liu.entity.User;
import com.liu.vo.MobileSchedulingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 排课子系统的关于页面模板初始化的controller
 */
@Controller
public class SchedulingControllerPage {
    @Autowired
    SchedulingDao schedulingDao;

    @GetMapping("/classScheduling")
    public String classScheduling(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer sno = user.getAccount();
        Integer majorId = user.getMajorid();
        /**
         * 这里需要判断哪些老师有权限排课，属于后期功能
         * 暂且不表
         */
        return "classscheduling";
    }

    @GetMapping("/schedulingmobile")
    public String schedulingmobile(@RequestParam("tno") Integer tno, @RequestParam("tname") String tname,
                                   @RequestParam("address") String address, Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<MobileSchedulingView> data = schedulingDao.getCoursesByTnoAndTnameAndAddress(tno, tname, address);
        model.put("data", data);
        /**
         * 这里需要判断哪些老师有权限排课，属于后期功能
         * 暂且不表
         */
        return "schedulingmobile";
    }

    @GetMapping("/schedulingsearchmobile")
    public String schedulingsearchmobile(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        return "schedulingsearchmobile";
    }
}
