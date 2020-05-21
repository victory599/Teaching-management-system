package com.liu.entity;
import lombok.Data;

/**
 * 课程
 */
@Data
public class Course {
    private Integer cno;
    private String cname;
    private String college;             // 学院
    private String description;         // 简介
    private String status;              // 状态：true/false

    public Course(Integer cno, String cname, String college, String description, String status) {
        this.cno = cno;
        this.cname = cname;
        this.college = college;
        this.description = description;
        this.status = status;
    }
    public Course(){}
}
