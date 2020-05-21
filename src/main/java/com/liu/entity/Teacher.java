package com.liu.entity;

import lombok.Data;

/**
 * 教师实体
 */
@Data
public class Teacher {
    private Integer tno;
    private String tname;
    private String sex;
    private String phone;
    private String email;
    private Integer collegeId;      // 所在学院
    private String office;          // 办公室地址
    private String rank;            // 职称：教授，副教授

    public Teacher() {
    }

    public Teacher(Integer tno, String tname, String sex, String phone, String email, Integer collegeId, String office, String rank) {
        this.tno = tno;
        this.tname = tname;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.collegeId = collegeId;
        this.office = office;
        this.rank = rank;
    }
}
