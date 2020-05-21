package com.liu.dao;

import com.liu.entity.QuestionStudentChoose;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface QuestionStudentDao {
    // 学生选择某个题目
    public void chooseQuestion(HashMap map);

    // 学生根据学号查询自己选过的所有题目
    public List<QuestionStudentChoose> getChoiceBySno(int sno);

    // 老师根据工号查询选自己课题的所有学生
    public List<QuestionStudentChoose> getChoiceByTno(int tno);

    // 根据学生学号和论题号查询，用来判断是否成功插入选题表
    public QuestionStudentChoose getChoiceByQidSno(HashMap map);

    // 查找选择该题的所有学生
    public List<QuestionStudentChoose> getChoiceByQid(int questionid);


    // 学生删除某个题目
    //public void deleteQuestionStudent(HashMap map);

    // 学生根据学号查询所有自己选过的题目
    //public List<Question> getQuestionOfStudent(int sno);

    // 老师查看自己选题的学生 由于需要其他表，暂时做只能获取学号的
    //public List<QuestionStudentChoose> TeacherStudent(int )

    // 后台获取所有选题信息
    //public List<QuestionStudentChoose> getAllChoice();
}
