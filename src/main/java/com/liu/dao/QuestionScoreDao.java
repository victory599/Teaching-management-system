package com.liu.dao;


import com.liu.entity.QuestionScore;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yuzhongrui
 */

@Repository
public interface QuestionScoreDao {
    //添加成绩
    public void addQuestionScore(QuestionScore questionScore);


    public QuestionScore getQuestionScoreBySno(int sno);

    public List<QuestionScore> getQuestionScoreByTno(int tno);

    public List<QuestionScore> getAllQuestionScore();

    public void changeQuestionScore(QuestionScore questionScore);

}
