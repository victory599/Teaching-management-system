package com.liu.entity;

import lombok.Data;

/**
 * 学生实体
 */
@Data
public class Student {
    // 个人基本信息
    private Integer sno;
    private String sname;
    private String sex;
    private String major;
    private String klass;           // 班级，避开class关键字，将c改成k
    private String comeYear;
    private String phone;
    private String college;         // 学院
    private Integer collegeId;      // 学院id
    private String grade;           // 年级，不是分数
    private Integer majorId;        // 专业id

    public Student(Integer sno, String sname, String sex, String major, String klass, String comeYear,
                   String phone, String college, Integer collegeId, String grade, Integer majorId) {
        this.sno = sno;
        this.sname = sname;
        this.sex = sex;
        this.major = major;
        this.klass = klass;
        this.comeYear = comeYear;
        this.phone = phone;
        this.college = college;
        this.collegeId = collegeId;
        this.grade = grade;
        this.majorId = majorId;
    }

    public Student() {}
}
