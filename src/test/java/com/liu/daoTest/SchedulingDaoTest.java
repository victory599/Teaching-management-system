package com.liu.daoTest;

import com.liu.dao.SchedulingDao;
import com.liu.vo.MobileSchedulingView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SchedulingDaoTest {
    @Autowired
    SchedulingDao schedulingDao;

    @Test
    public void test() {
        List<MobileSchedulingView> courses = schedulingDao.getCoursesByTnoAndTnameAndAddress(1000000, "陈雪", "图书");
        System.out.println(courses);
    }
}
