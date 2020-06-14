package com.liu.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("TeaCourseView")
public class TeaCourseView{
    private int semesterid;
    private int cno;
    private String cname;
    private int capacity;
    private String address;
    private String courseTime;

    public TeaCourseView(){}

    public TeaCourseView(int semesterid,int cno,int capacity,String address,String courseTime,String cname){
        this.semesterid = semesterid;
        this.cno = cno;
        this.cname = cname;
        this.capacity = capacity;
        this.address = address;
        this.courseTime = courseTime;
    }
}