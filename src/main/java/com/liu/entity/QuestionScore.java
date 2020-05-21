package com.liu.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * 课题成绩
 */
@Data
@Alias("QuestionScore")
public class QuestionScore {
    private int sno;
    private int questionid;
    private int earlyperformance;
    private int midexam;
    private int thesisanswer;
    private int paper;
    private int extracredit;

    public QuestionScore() {
    }

    public QuestionScore(int sno, int questionid, int earlyperformance, int midexam, int thesisanswer, int paper, int extracredit) {
        this.sno = sno;
        this.questionid = questionid;
        this.earlyperformance = earlyperformance;
        this.midexam = midexam;
        this.thesisanswer = thesisanswer;
        this.paper = paper;
        this.extracredit = extracredit;
    }
}
