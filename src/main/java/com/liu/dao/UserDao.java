package com.liu.dao;

import com.liu.entity.User;
import com.liu.views.UserAddView;
import org.springframework.stereotype.Repository;

/**
 * @author 高谦
 * 用户 User 表 数据库操作类。
 * UserDao
 */

@Repository
public interface UserDao {
    public User Login(User user);
    void addUser(UserAddView user);
    void updateUserPassword(UserAddView user);

    User getByAccount(Integer account);
}
