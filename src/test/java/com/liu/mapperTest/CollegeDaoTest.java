package com.liu.mapperTest;

import com.liu.dao.CollegeDao;
import com.liu.dao.UserDao;
import com.liu.entity.College;
import com.liu.service.CollegeService;
import com.liu.service.UserService;
import com.liu.views.UserAddView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollegeDaoTest {
    @Autowired
    CollegeDao collegeDao;
    @Autowired
    CollegeService collegeService;
    @Autowired
    UserDao userDao;
    @Autowired
    private DataSource dataSource;
    @Autowired
    UserService userService;
    @Test
    public void getALL(){
        List<College> colleges = collegeDao.getColleges();
        System.out.println(colleges);
    }
    @Test
    public void getCollegeByName(){
        UserAddView user = new UserAddView();
        user.setUserStatus(2);
        user.setUserType(2);
        user.setUserPassword("2222");
        user.setUserAccount(9090901);
        userService.upDateUserPassword(user,"1234");

        System.out.println(user);
//        College college = collegeDao.getCollegeByName("化学工程学院");
    }
}
