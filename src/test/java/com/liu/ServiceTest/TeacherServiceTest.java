package com.liu.ServiceTest;

import com.liu.entity.Teacher;
import com.liu.service.TeacherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherServiceTest {
    @Autowired
    TeacherService teacherService;

    @Test
    public void addTeachers() {
        Teacher t1 = new Teacher();
        t1.setCollegeId(1);
        t1.setEmail("2");
        t1.setOffice("2");
        t1.setRank("22");
        t1.setTno(212);
        t1.setSex("2");
        t1.setPhone("2q1");
        t1.setTname("222");

        Teacher t2 = new Teacher();
        t2.setCollegeId(1);
        t2.setEmail("2");
        t2.setOffice("2");
        t2.setRank("22");
        t2.setTno(213);
        t2.setSex("2");
        t2.setPhone("2q1");
        t2.setTname("222");

        List<Teacher> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);
        teacherService.addTeachers(list);
    }

    @Test
    public void updateTeachers() {
        Teacher t1 = new Teacher();
        t1.setCollegeId(1);
        t1.setEmail("2");
        t1.setOffice("2");
        t1.setRank("22");
        t1.setTno(213);
        t1.setSex("2");
        t1.setPhone("2q1");
        t1.setTname("springss");

        Teacher t2 = new Teacher();
        t2.setCollegeId(1);
        t2.setEmail("2");
        t2.setOffice("2");
        t2.setRank("22");
        t2.setTno(212);
        t2.setSex("2");
        t2.setPhone("2q1");
        t2.setTname("springxml");

        List<Teacher> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);
        teacherService.updateTeachers(list);
    }

    @Test
    public void addTeacher() {
        Teacher t1 = new Teacher();
        t1.setCollegeId(1);
        t1.setEmail("2");
        t1.setOffice("2");
        t1.setRank("22");
        t1.setTno(214);
        t1.setSex("2");
        t1.setPhone("2q1");
        t1.setTname("222");
        teacherService.addTeacher(t1);
    }

    @Test
    public void updateTeacherById() {
        Teacher t1 = new Teacher();
        t1.setCollegeId(1);
        t1.setEmail("2");
        t1.setOffice("2");
        t1.setRank("22");
        t1.setTno(214);
        t1.setSex("2");
        t1.setPhone("sss");
        t1.setTname("222");
        teacherService.updateTeacherById(214, t1);
    }
}
