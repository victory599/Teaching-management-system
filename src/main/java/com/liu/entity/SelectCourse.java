package com.liu.entity;

import lombok.Data;

/**
 * 选课成绩信息
 */
@Data
public class SelectCourse {
    private Integer semesterId;         // 学期id
    private Integer cno;                // 学号
    private Integer sno;                // 课程号
    private Integer totalScore;         // 总成绩
    private String detail;              // 成绩分布

    private Integer addition;           // 这个参数别管他，没用！但是也别删

    public SelectCourse(Integer semesterId, Integer cno, Integer sno, Integer grade, String detail) {
        this.semesterId = semesterId;
        this.cno = cno;
        this.sno = sno;
        this.totalScore = grade;
        this.detail = detail;
    }
    public SelectCourse(){}
}
