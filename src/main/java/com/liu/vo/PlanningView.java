package com.liu.vo;

import lombok.Data;

@Data
public class PlanningView {
    private Integer cno;
    private String cname;
    private String college;
    private String description;
    private Boolean ifOpen;
}
