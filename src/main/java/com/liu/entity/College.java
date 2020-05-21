package com.liu.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * 学院表 domain 类
 */
@Data
@Alias(value = "college")
public class College {
    private Integer collegeId;
    private String collegeName;
    private String phone;
    private String address;
    private String description;     // 学校简介

    public College(Integer collegeId, String collegeName, String address, String phone, String description) {
        this.collegeId = collegeId;
        this.collegeName = collegeName;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }
    public College() {}
}
