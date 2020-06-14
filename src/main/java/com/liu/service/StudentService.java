package com.liu.service;

import com.liu.dao.StudentDao;
import com.liu.entity.Student;
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

/**
 * 学生表相关服务
 */
@Service
public class StudentService {
    @Autowired
    StudentDao studentDao;

    /**
     * 根据学号获得学生信息
     * @param sno
     * @return
     */
    public Student getStudentBySno(Integer sno) {
        return studentDao.getBySno(sno);
    }

    public List<Student> getAllStudent() {
        return studentDao.getAll();
    }

    public List<Student> getStudentsByExample(Student student) {
        return studentDao.getStudentsByExample(student);
    }

    @Transactional
    public void saveStudents(List<Student> students) {
        for (Student student : students) {
            studentDao.insertStudnet(student);
        }
    }

    public void saveStudent(Student student) {
        studentDao.insertStudnet(student);
    }

    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }

    public boolean excel(File file) {
        HSSFWorkbook hssfWorkbook = null;
        Student student = null;
        List<Student> students = new LinkedList<>();
        try {
            hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 1; i <= lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);

            /* 注意：注释内方式获取数字，默认是科学计数法，因此需要引入DataFormatter调整格式
            student = new Student(
                    Integer.parseInt(row.getCell(0).toString()),
                    row.getCell(1).toString(),
                    row.getCell(2).toString(),
                    row.getCell(3).toString(),
                    row.getCell(4).toString(),
                    row.getCell(5).toString(),
                    row.getCell(6).toString(),
                    row.getCell(9).toString(),
                    Integer.parseInt(row.getCell(10).toString()),
                    row.getCell(7).toString(),
                    Integer.parseInt(row.getCell(8).toString())
            );*/
            DataFormatter df = new DataFormatter();
            student = new Student(
                    Integer.parseInt(df.formatCellValue(row.getCell(0))),
                    df.formatCellValue(row.getCell(1)),
                    df.formatCellValue(row.getCell(2)),
                    df.formatCellValue(row.getCell(3)),
                    df.formatCellValue(row.getCell(4)),
                    df.formatCellValue(row.getCell(5)),
                    df.formatCellValue(row.getCell(6)),
                    df.formatCellValue(row.getCell(9)),
                    Integer.parseInt(df.formatCellValue(row.getCell(10))),
                    df.formatCellValue(row.getCell(7)),
                    Integer.parseInt(df.formatCellValue(row.getCell(8)))
            );

            students.add(student);
        }
        saveStudents(students);
        return true;
    }
}