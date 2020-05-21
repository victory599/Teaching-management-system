package com.liu.entity;

import lombok.Data;

/**
 * 学期信息
 */
@Data
public class Semester {
    private Integer semesterId;
    private String start;           // 起始年
    private String end;             // 结束年
    private String semester;        // 第几学期

    public Semester(Integer semesterId,String start, String end, String semester) {
        this.semesterId=semesterId;
        this.start = start;
        this.end = end;
        this.semester = semester;
    }
    public Semester() {}
    @Override
    public String toString(){
        return this.start+"-"+this.end+"学年第"+this.semester+"学期";
    }
}
