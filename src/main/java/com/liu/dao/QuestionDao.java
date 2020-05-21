package com.liu.dao;

import com.liu.entity.Question;
import com.liu.entity.QuestionStudentInquiry;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface QuestionDao {
    // 学生端
    // 根据专业id，获取论题的部分内容，返回信息列表
    public List<QuestionStudentInquiry> getPartQuestionsByMajorid(int majorid);

    // 根据论题id获取论题的全部信息，返回一个课题信息
    public Question getQuestionByQustionId(int QuestionId);


    // 教师端
    // 添加论题
    public void addQuestion(Question question);

    // 根据论题号删除论题，不能删除已经被选中的课题
    public void deleteQuestion(int questionId);

    // 确定论题和学生之间的连接
    public void sureQuestionStudent(HashMap map);

    // 根据老师工号获取论题全部内容，返回论题列表
    public List<Question> getQuestionByTno(int tno);


    // 管理员端
    // 根据专业 获取论题的全部内容，返回论题信息列表
    public List<Question> getQuestionsByMajorid(int majorid);

    public List<Question> getAllQuestions();

    // 删除学生和选题之间的连接
    public void deleteStudentQuestion(int questionid);

    // 根据学号查找论题，确认学生是否已经有选题
    public Question getSingleQuestionBySno(int sno);
}
