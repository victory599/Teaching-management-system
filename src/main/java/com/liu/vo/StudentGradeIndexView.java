package com.liu.vo;

import lombok.Data;

/**
 * 显示成绩栏目的 view
 */
@Data
public class StudentGradeIndexView {
    private Integer start;
    private Integer end;
    private Integer semester;
    private Integer cno;
    private String cname;
    private Integer totalScore;
    public StudentGradeIndexView(){}
}
