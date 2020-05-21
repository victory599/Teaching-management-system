package com.liu.entity;

import lombok.Data;

/**
 * 专业的课表
 */
@Data
public class ScheduleMajor {
    private Integer semesterId;         // 学期id
    private Integer cno;                // 课程id
    private Integer majorId;            // 专业id

    public ScheduleMajor(Integer semesterId, Integer cno, Integer majorId) {
        this.semesterId = semesterId;
        this.cno = cno;
        this.majorId = majorId;
    }

    public ScheduleMajor() {}
}
