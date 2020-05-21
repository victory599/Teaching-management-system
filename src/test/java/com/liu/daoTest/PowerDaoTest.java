package com.liu.daoTest;

import com.liu.dao.PowerDao;
import com.liu.entity.Power;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerDaoTest {
    @Autowired
    PowerDao powerDao;

    @Test
    public void getPower() {
        List<Power> power = powerDao.getPower();
        System.out.println(power.get(0));
    }

    @Test
    public void updatePower() {
        Power power = new Power();
        power.setAbnormal(1);
        power.setScore(0);
        power.setSelectCourse(1);
        powerDao.updatePower(power);
    }

    @Test
    public void insertPower() {
        Power power = new Power();
        power.setAbnormal(1);
        power.setScore(0);
        power.setSelectCourse(0);
        powerDao.insertPower(power);
    }

    @Test
    public void deletePower() {
        powerDao.deletePower();
    }
}
