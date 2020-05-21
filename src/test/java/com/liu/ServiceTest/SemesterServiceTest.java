package com.liu.ServiceTest;

import com.liu.entity.Semester;
import com.liu.service.SemesterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 学期相关业务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SemesterServiceTest {
    @Autowired
    SemesterService semesterService;

    @Test
    public void getCurrentSemesterInfo() {
        Semester currentSemesterInfo = semesterService.getCurrentSemesterInfo();
        System.out.println(currentSemesterInfo);
    }

    @Test
    public void getCurrentSemesterId() {
        Integer currentSemesterId = semesterService.getCurrentSemesterId();
        System.out.println(currentSemesterId);
    }

    @Test
    public void getSemesterList() {
        List<String> semesterList = semesterService.getSemesterList();
        System.out.println(semesterList);
    }

    @Test
    public void getSemesterEntity() {
        List<Semester> semesterList = semesterService.getSemesterEntity();
        System.out.println(semesterList);
    }
}
