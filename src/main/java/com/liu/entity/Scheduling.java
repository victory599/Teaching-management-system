package com.liu.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;


/**
 * 课表
 */
@Data
@Alias("Scheduling")
public class Scheduling {
    private Integer semesterId;     // 学期id
    private Integer cno;            // 课程号
    private Integer tno;            // 上课老师的工号
    private String status;          // 课程状态（停开/开设）
    private Integer capacity;       // 课程容量
    private String address;         // 上课地点
    private String percent;         // 分数占比，成绩管理系统系统设置值，排课系统不用 care
    private String majorGrade;      // 专业的年级
    private String courseTime;      // 上课时间


    public Scheduling(Integer semesterId, Integer cno, Integer tno, String status, Integer capacity,
                      String address, String courseTime, String percent, String majorGrade) {
        if (semesterId != -1)
            this.semesterId = semesterId;
        else
            this.semesterId = null;
        this.cno = cno;
        this.tno = tno;
        this.status = status;
        if (capacity != -1)
            this.capacity = capacity;
        else
            this.capacity = null;
        this.address = address;
        this.courseTime = courseTime;
        this.percent = percent;
        this.majorGrade = majorGrade;
    }

    public Scheduling() {}
}
