package com.liu.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 统一的进行登录验证，如果没哟登录，则直接返回到原来的界面
 * 这个类中的代码如需要改动，先发issue，关于重定向的问题，请确保非常清楚的前提下再进行代码修改！
 */
@Aspect
@Component
public class PageAspect {
    /**
     * 在CollegeController下面的所有方法执行之前先执行该代码，比如是否登录的判断等逻辑，这就不用在每个方法中都写判断逻辑了
     */
    @Pointcut("execution(public * com.liu.controller.*Page.*(..))")
    public void login() {
    }

    /**
     * 权限拦截器，没有登录就无法进行操作，只可以浏览登录页面
     * @param joinPoint
     * @throws Throwable
     * @return 可能是一个到登录页面的重定向，也有可能是拦截完成到对用的地址。
     */
    @Around("login()")
    public Object islogin(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            return "redirect:/index";
        }
        return joinPoint.proceed();
    }
}
