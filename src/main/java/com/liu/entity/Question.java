package com.liu.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * 课题
 */
@Data
@Alias(value = "Question")
public class Question {
    private int questionid;     // 题目id
    private String topic;       // 课题标题
    private String content;     // 课题简介
    private int difficulty;     // 标注论题的难度
    private int tno;            // 老师工号
    private int majorid;        // 专业id
    private int sno;            // 学生学号
    private boolean ischosen;   // 是否选中

    public Question() {
    }

    public Question(String topic, String content, int difficulty, int tno, int majorid) {
        this.topic = topic;
        this.content = content;
        this.difficulty = difficulty;
        this.tno = tno;
        this.majorid = majorid;
    }

    public Question(int questionid, String topic, String content, int difficulty, int tno, int majorid, int sno, boolean ischosen) {
        this.questionid = questionid;
        this.topic = topic;
        this.content = content;
        this.difficulty = difficulty;
        this.tno = tno;
        this.majorid = majorid;
        this.sno = sno;
        this.ischosen = ischosen;
    }

    /*public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public void setIschosen(boolean ischosen) {
        this.ischosen = ischosen;
    }

    public int getQuestionid() {
        return questionid;
    }

    public String getTopic() {
        return topic;
    }

    public boolean isIschosen() {
        return ischosen;
    }*/
}


