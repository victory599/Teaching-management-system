package com.liu.dao;

import com.liu.entity.College;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 高谦
 * 学院 college 表 数据库操作类。
 */

@Repository
public interface CollegeDao {

    public List<College> getColleges();

    public College getCollegeById(String id);

    public List<String> getCollegeNames();
    public String getColNameById(int id);
}

