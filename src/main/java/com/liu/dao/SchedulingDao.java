package com.liu.dao;

import com.liu.entity.Scheduling;
import com.liu.entity.SelectCourse;
import com.liu.vo.MobileSchedulingView;
import com.liu.vo.SchedulingCourseView;
import com.liu.vo.SchedulingView;
import com.liu.vo.TeaCourseView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课表操作
 */
@Repository
public interface SchedulingDao {
    public SchedulingView getCourse(@Param("semesterId") Integer semesterId, @Param("cno") Integer cno);

    public Boolean deleteCourse(@Param("semesterId") Integer semesterId, @Param("cno") Integer cno);

    public Boolean deleteCourseMajor(@Param("semesterId") Integer semesterId, @Param("cno") Integer cno, @Param("majorId") Integer majorId, @Param("grade") Integer grade);

    public Boolean addCourse(@Param("semesterId") Integer semesterId, @Param("cno") Integer cno, @Param("tno") Integer tno,
                             @Param("status") String status, @Param("capacity") Integer capacity, @Param("address") String address, @Param("grade") Integer grade,
                             @Param("time") String time);

    public Boolean addCourseMajor(@Param("semesterId") Integer semesterId, @Param("cno") Integer cno, @Param("majorId") Integer majorId, @Param("grade") Integer grade);

    public Integer getCourseMajorCount(@Param("semesterId") Integer semesterId, @Param("cno") Integer cno);

    public Integer checkCourseMajor(@Param("semesterId") Integer semesterId, @Param("cno") String cno, @Param("majorId") Integer majorId, @Param("grade") Integer grade);

    public List<MobileSchedulingView> getCoursesByTnoAndTnameAndAddress(@Param("tno") Integer tno, @Param("tname") String tname, @Param("address") String address);

    public List<SchedulingCourseView> getAllCourses(@Param("year") String year, @Param("semester") String semester,
                                                    @Param("majorName") String majorName, @Param("grade") Integer grade);

    //几个用于检测冲突的查询方法
    /**
     * 获取教师(学生)在这个学期已经安排好的任务的时间字符串列表
     * @return 返回老师的任务时间列表
     */
    public List<String> getTeacherTaskTime(Scheduling scheduling);

    public List<String> getStudentTaskTime(SelectCourse selectCourse);

    // 查询某个教室的所有使用时间
    public List<String> getRoomTaskTime(Scheduling scheduling);

    public List<TeaCourseView> getCourseInfoByTno(int tno);
}
