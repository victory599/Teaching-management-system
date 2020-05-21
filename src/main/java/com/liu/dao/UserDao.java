package com.liu.dao;

import com.liu.entity.User;
import com.liu.vo.UserAddView;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    public User Login(User user);

    void addUser(UserAddView user);

    void updateUserPassword(UserAddView user);

    User getByAccount(Integer account);
}
