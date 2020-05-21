package com.liu.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("QuestionStudentChoose")
public class QuestionStudentChoose {
    private int questionid;
    private int sno;

    public QuestionStudentChoose(int questionid,int sno){
        this.questionid = questionid;
        this.sno = sno;
    }
    public QuestionStudentChoose() {}
}
