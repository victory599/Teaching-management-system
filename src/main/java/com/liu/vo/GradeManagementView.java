package com.liu.vo;

import lombok.Data;

/**
 * 说明：
 *  该包下的实体类不是数据库映射实体，而是在具体的应用接口中，根据实际需要由多表数据连接而成的
 */
@Data
public class GradeManagementView {
    // 公共字段
    private Integer semesterId;
    private Integer cno;
    private Integer sno;
    private Integer tno;

    // 课程表特有字段
    private String cname;
    private String college;
    private String percent;

    // 选课表
    private String totalscore;
    private String detail;

    // planning表
    private Integer credit;

    // semester表
    private String start;
    private String end;
    private String semester;
}
