package com.liu.service;

import com.liu.dao.TeacherDao;
import com.liu.dao.UserDao;
import com.liu.entity.Teacher;
import com.liu.vo.UserAddView;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    UserDao userDao;

    public Teacher getTeacherByTno(Integer tno) {
        Teacher teacher = teacherDao.getByTno(tno);
        return teacher;
    }

    @Transactional
    public void addTeachers(List<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            teacherDao.insertTeacher(teacher);
        }
    }

    @Transactional
    public void updateTeachers(List<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            teacherDao.updateTeacher(teacher);
        }
    }

    public void addTeacher(Teacher teacher) {
        teacherDao.insertTeacher(teacher);
        if (teacher.getRank().equals("root")) {
            UserAddView user = new UserAddView();
            user.setUserAccount(teacher.getTno());
            user.setUserPassword(teacher.getTno().toString());
            user.setUserType(2);
            user.setUserStatus(1);
            userDao.addUser(user);
        }
    }

    public void updateTeacherById(Integer id, Teacher teacher) {
        teacher.setTno(id);
        teacherDao.updateTeacher(teacher);
    }

    public List<Teacher> getAllTeacher() {
        List<Teacher> teachers = teacherDao.queryByExample(new Teacher());
        return teachers;
    }

    public List<Teacher> getTeacherByExample(Teacher teacher) {
        return teacherDao.queryByExample(teacher);
    }

    public boolean excel(File file) {
        HSSFWorkbook hssfWorkbook = null;
        Teacher teacher = null;
        List<Teacher> teachers = new LinkedList<>();
        try {
            hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        float tno = 0;
        float coid = 0;
        for (int i = 1; i <= lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            DataFormatter df = new DataFormatter();
            teacher = new Teacher();
            teacher.setTname(df.formatCellValue(row.getCell(1)));
            teacher.setSex(df.formatCellValue(row.getCell(2)));
            teacher.setCollegeId(Integer.parseInt(df.formatCellValue(row.getCell(5))));
            teacher.setEmail(df.formatCellValue(row.getCell(4)));
            teacher.setPhone(df.formatCellValue(row.getCell(3)));
            teacher.setOffice(df.formatCellValue(row.getCell(6)));
            teacher.setTno(Integer.parseInt((df.formatCellValue(row.getCell(0)))));
            teacher.setRank(df.formatCellValue(row.getCell(7)));

            teachers.add(teacher);
        }
        addTeachers(teachers);
        return true;
    }
}