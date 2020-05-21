package com.liu.vo;

import lombok.Data;

@Data
public class SchedulingView {
    private Integer tno;
    private String address;
    private String time;
    private String status;
    private Integer capacity;

    public SchedulingView(Integer tno, String address, String time, String status, Integer capacity) {
        this.tno = tno;
        this.address = address;
        this.time = time;
        this.status = status;
        this.capacity = capacity;
    }
}

