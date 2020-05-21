package com.liu.dao;

import com.liu.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程相关
 */
@Repository
public interface CourseDao {
    public List<Course> getAll();

    public Course getByCno(String cno);

    public Course getByName(String cno);

    // 添加课表
    public Boolean addCourse(@Param("cno") Integer cno, @Param("cname") String cname, @Param("college") String college, @Param("description") String description, @Param("status") String status);

    // 检查课表是否存在：通过主键查询数据条数并返回（0或1）
    public Boolean checkCourse(Integer cno);

    public Integer getCnoByCname(String cname);
}
