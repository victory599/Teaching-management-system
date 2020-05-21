package com.liu.dao;

import com.liu.entity.College;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学院相关
 */
@Repository
public interface CollegeDao {

    public List<College> getColleges();

    public College getCollegeById(String id);

    // 获取学院名
    public List<String> getCollegeNames();

    public String getColNameById(int id);
}

