package com.liu.service;

import com.liu.dao.SemesterDao;
import com.liu.entity.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 学期表业务功能
 */
@Service
public class SemesterService {
    @Autowired
    SemesterDao semesterDao;

    public Semester getCurrentSemesterInfo() {
        Semester semester = semesterDao.getSemesterMostId();
        return semester;
    }

    public Integer getCurrentSemesterId() {
        /*List<Semester> all = semesterDao.getSemesterMostId();
        return all.get(0).getSemesterId();*/
        Semester semester = semesterDao.getSemesterMostId();
        return semester.getSemesterId();
    }

    public List<String> getSemesterList() {
        List<Semester> all = semesterDao.getAll();
        List<String> semesterList = new ArrayList<>();
        for (Semester semester : all) {
            semesterList.add(semester.toString());
        }
        return semesterList;
    }

    public List<Semester> getSemesterEntity() {
        List<Semester> all = semesterDao.getAll();
        return all;
    }

    /**
     * 根据条件查询学期
     * @param start    学年起始年
     * @param semester 学期 【1,2,3】
     * @return 返回特定的学期
     */
    public Semester getSemesterByStartAndSemester(Integer start, Integer semester) {
        Semester parm = new Semester();
        parm.setStart(start.toString());
        parm.setSemester(semester.toString());
        Semester res = semesterDao.getSemesterByStartAndSemester(parm);
        return res;
    }

    public Semester getSemesterById(Integer semesterId) {
        return semesterDao.getSemesterById(semesterId);
    }

    public void addSemester(Semester semester) {
        semesterDao.insertSemester(semester);
    }
}