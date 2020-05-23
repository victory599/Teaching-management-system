package com.liu.dao;


import com.liu.entity.Semester;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学期相关
 */
@Repository
public interface SemesterDao {
    public Integer getSemesterId(@Param("year") String year, @Param("semester") String semester);

    public void insertSemester(Semester semester);

    public List<Semester> getAll();

    //public List<Semester> getSemesterMostId();
    public Semester getSemesterMostId();

    /**
     * 查询特定的学期
     * @param parm  查询条件数据集合
     * @return
     */
    Semester getSemesterByStartAndSemester(Semester parm);

    Semester getSemesterById(Integer semesterId);
}
