package com.liu.dao;

import com.liu.entity.Course;
import com.liu.entity.SelectCourse;
import com.liu.vo.GradeManagementView;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 查询成绩相关信息
 */
@Repository
public interface GradeManagementDao {

    /* 学生端 */
    /**
     * 根据查询条件返回结果
     * @param grade 条件集合
     * @return  返回查询到的结果
     */
    List<GradeManagementView> getCourseGrade(GradeManagementView grade);

    /* 教师端 */
    /**
     * 根据查询条件返回结果
     * @param studentGrade 条件集合
     * @return  学号为空时返回所有学生的成绩，不为空返回一个学生的成绩
     */
    List<GradeManagementView> getStudentsGrade(GradeManagementView studentGrade);


    /**
     * 修改某一个学生的成绩
     * @return  是否修改成功
     */
    Integer setStudentGradeById(SelectCourse studentGrade);


    /**
     * 获取某个课程的成绩百分比
     * @return  返回成绩百分比
     */
   String getCoursePercent(GradeManagementView percent);

    /**
     * 获取某个老师该学期所有课的cno和cname
     */
    List<Course> getCourseOfTeacher(GradeManagementView teachercourse);
}
