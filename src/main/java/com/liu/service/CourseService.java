package com.liu.service;

import com.liu.dao.CourseDao;
import com.liu.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseDao courseDao;

    public List<Course> getAll(){
        return courseDao.getAll();
    }

    public Course getByName(String cno){
        if(cno!=null){
            return null;
        }
        else{
            return courseDao.getByCno(cno);
        }
    }

    /**
     * @author 王艺琳
     * 通过课程名称获取课程号
     * @param cname
     * @return
     */
    public Integer getCnoByCname(String cname){
        if(cname==null){
            return null;
        }
        else{
            System.out.println("!!!"+courseDao.getCnoByCname(cname));
            return courseDao.getCnoByCname(cname);
        }
    }
}
