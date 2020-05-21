package com.liu.dao;

import com.liu.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherDao {
    public Teacher getByTno(Integer tno);

    public void insertTeacher(Teacher teacher);

    public List<Teacher> queryByExample(Teacher example);

    public void updateTeacher(Teacher teacher);

    public List<Teacher> getTeacherByTnoAndTname(@Param("tno") Integer tno, @Param("tname") String tname);
}
