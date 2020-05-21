package com.liu.mapperTest;

import com.liu.dao.TeacherDao;
import com.liu.entity.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
/**
 * @author 孟庆强
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherDaoTest {
    @Autowired
    TeacherDao teacherDao;
    @Test
    public void insertTeacher(){
        Teacher teacher = new Teacher();
        teacher.setCollegeId(1);
        teacher.setEmail("2");
        teacher.setOffice("2");
        teacher.setRank("22");
        teacher.setTno(21);
        teacher.setSex("2");
        teacher.setPhone("2q1");
        teacher.setTname("222");
        teacherDao.insertTeacher(teacher);
    }
    @Test
    public void queryByExample(){
        Teacher teacher = new Teacher();
//        teacher.setEmail("2");
//        teacher.setOffice("2");
//        teacher.setRank("22");
//        teacher.setCollegeId(1);
//        teacher.setSex("2");
//        teacher.setPhone("2q1");
            teacher.setTname("tom");
        List<Teacher> teachers = teacherDao.queryByExample(teacher);
        System.out.println(teachers);
    }
    @Test
    public void updateTeacher(){
        Teacher teacher = new Teacher();
        teacher.setEmail("2020202@gmail.com");
        teacher.setOffice("intn");
        teacher.setRank("doc");
        teacher.setCollegeId(1);
        teacher.setSex("男");
        teacher.setPhone("1303213321");
        teacher.setTname("sim");
        teacher.setTno(21);
        teacherDao.updateTeacher(teacher);
    }
}
