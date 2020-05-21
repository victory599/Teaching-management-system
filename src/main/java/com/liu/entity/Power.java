package com.liu.entity;

import lombok.Data;

/**
 * 权限控制
 */
@Data
public class Power {
    private Integer selectCourse;       // 选课开关
    private Integer score;              // 录入成绩的权限
    private Integer abnormal;           // 学籍异动
}
