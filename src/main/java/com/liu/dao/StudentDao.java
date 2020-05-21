package com.liu.dao;

import com.liu.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {
    public Student getBySno(Integer sno);

    public List<Student> getAll();

    public void insertStudnet(Student student);

    public void updateStudent(Student student);

    List<Student> getStudentsByExample(Student student);
}
