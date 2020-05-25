package com.liu.controller;

import com.liu.entity.*;
import com.liu.service.QuestionService;
import com.liu.service.QuestionStudentChooseService;
import com.liu.service.StudentService;
import com.liu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private QuestionStudentChooseService questionStudentChooseService;

    @RequestMapping("/StuLookThroughQues")
    public String StuLookThroughQues(HttpServletRequest request, Map<String, Object> map) {
        // 需要返回的列表数据有 题目 难度 出题老师姓名 是否选中
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int sno = user.getAccount();
        Student student = studentService.getStudentBySno(sno);
        int studentMajor = student.getMajorId();
        List<QuestionStudentInquiry> questionStudentInquiry = questionService.getPartQuestionByMajorid(studentMajor);
        map.put("quesInfos", questionStudentInquiry);
        return "StuLookThroughQues";
    }

    @RequestMapping("/StuLookThroughQuesMobile")
    public String StuLookThroughQuesMobile(HttpServletRequest request, Map<String, Object> map) {
        // 需要返回的列表数据有 题目 难度 出题老师姓名 是否选中
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int sno = user.getAccount();
        Student student = studentService.getStudentBySno(sno);
        int studentMajor = student.getMajorId();
        List<QuestionStudentInquiry> questionStudentInquiry = questionService.getPartQuestionByMajorid(studentMajor);
        map.put("quesInfos", questionStudentInquiry);
        return "StuLookThroughQuesMobile";
    }

    @RequestMapping("/StuQuesDetails")
    public String StuQuesDetails(HttpServletRequest request, Map<String, Object> map, @RequestParam("questionid") int questionid) {
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int sno = user.getAccount();
        Question question = questionService.getSingleQuestionByQuestionid(questionid);
        int tno = question.getTno();
        Teacher teacher = teacherService.getTeacherByTno(tno);
        QuestionStudentChoose questionStudentChoose = questionStudentChooseService.getChoiceByQidSno(questionid, sno);
        int isChosen = -1;
        Object hasChangedObject = session.getAttribute("hasChanged");
        Object isChosenObject = session.getAttribute("isChosen");

        if (hasChangedObject == null || isChosenObject == null) {
            isChosen = -1;
        } else if (((boolean) hasChangedObject) == true) {
            if ((boolean) isChosenObject) isChosen = 1;
            else isChosen = 0;
            session.removeAttribute("hasChanged");
        } else {
            isChosen = -1;
        }
        map.put("isChosen", isChosen);
        map.put("quesInfo", question);
        map.put("teaInfo", teacher);
        return "StuQuesDetails";
    }

    @RequestMapping("/StuQuesDetailsMobile")
    public String StuQuesDetailsMobile(HttpServletRequest request, Map<String, Object> map, @RequestParam("questionid") int questionid) {
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int sno = user.getAccount();
        Question question = questionService.getSingleQuestionByQuestionid(questionid);
        int tno = question.getTno();
        Teacher teacher = teacherService.getTeacherByTno(tno);
        QuestionStudentChoose questionStudentChoose = questionStudentChooseService.getChoiceByQidSno(questionid, sno);
        boolean isChosen = false;   //检验该学生是否投递选择
        if (questionStudentChoose != null)
            isChosen = true;
        map.put("isChosen", isChosen);
        map.put("quesInfo", question);
        map.put("teaInfo", teacher);
        return "StuQuesDetailsMobile";
    }

    @RequestMapping("/ManageQues")
    public String ManageQues(HttpServletRequest request, Map<String, Object> map) {
        List<Question> questions = questionService.getAllQuestions();
        map.put("quesInfos", questions);
        return "ManageQues";
    }

    @PostMapping(value = "/ManageQues")
    public String ManageQues(HttpServletRequest request, @RequestParam("questionid") int questionid, Map<String, Object> map) {
        boolean isDeleted = questionService.deleteStudentQuestion(questionid);
        map.put("isDeleted", isDeleted);
        return "redirect:/ManageQues";
    }

    @RequestMapping("/TeaLookThroughQues")
    public String TeaLookThroughQues(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int tno = user.getAccount();
        List<Question> questions = questionService.getQuestionByTno(tno);
        map.put("quesInfos", questions);
        return "TeaLookThroughQues";
    }

    @RequestMapping("/TeaLookThroughQuesMobile")
    public String TeaLookThroughQuesMobile(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int tno = user.getAccount();
        List<Question> questions = questionService.getQuestionByTno(tno);
        map.put("quesInfos", questions);
        return "TeaLookThroughQuesMobile";
    }

    @RequestMapping("/TeaAddQues")
    public String TeaAddQues(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        Object isAddedObject = session.getAttribute("isAdded");
        Object hasChangedObject = session.getAttribute("hasChangedIsAdded");

        int isAddedJudge = -1;

        if (isAddedObject == null || hasChangedObject == null) {
            ;
        }else if ((boolean) hasChangedObject == true) {
            if ((boolean) isAddedObject == true) {
                isAddedJudge = 1;
            } else {
                isAddedJudge = 0;
            }
            session.removeAttribute("hasChangedIsAdded");
        }

        map.put("isAdded", isAddedJudge);
        return "TeaAddQues";
    }

    @RequestMapping("/TeaAddQuesMobile")
    public String TeaAddQuesMobile(HttpServletRequest request) {
        return "TeaAddQuesMobile";
    }

    @PostMapping(value = "/TeaAddQues")
    public String TeaAddQues(HttpServletRequest request, @RequestParam("topic") String topic, @RequestParam("content") String content,
                             @RequestParam("difficulty") int difficulty, @RequestParam("majorid") int majorid, Map<String, Object> map) {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        int tno = ((User) user).getAccount();
        Question question = new Question();
        question.setTno(tno);

        question.setTopic(topic);
        question.setContent(content);
        question.setDifficulty(difficulty);
        question.setMajorid(majorid);
        boolean isAdded = questionService.addQuestion(question);

        session.setAttribute("isAdded", isAdded);
        session.setAttribute("hasChangedIsAdded", true);

        return "redirect:/TeaAddQues";
    }

    @PostMapping(value = "/TeaAddQuesMobile")
    public String TeaAddQuesMobile(HttpServletRequest request, @RequestParam("topic") String topic, @RequestParam("content") String content,
                                   @RequestParam("difficulty") int difficulty, @RequestParam("majorid") int majorid, Map<String, Object> map) {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        int tno = ((User) user).getAccount();
        Question question = new Question();
        question.setTno(tno);

        question.setTopic(topic);
        question.setContent(content);
        question.setDifficulty(difficulty);
        question.setMajorid(majorid);
        boolean isAdded = questionService.addQuestion(question);
        map.put("isAdded", isAdded);
        return "redirect:/TeaAddQuesMobile";
    }

    @RequestMapping(value = "/TeaQuesDetails")
    public String TeaQuesDetails(HttpServletRequest request, @RequestParam("questionid") int questionid, Map<String, Object> map) {
        Question question = questionService.getSingleQuestionByQuestionid(questionid);
        map.put("question", question);
        List<QuestionStudentChoose> questionStudentChooses = questionStudentChooseService.getChoiceByQid(questionid);
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < questionStudentChooses.size(); i++) {
            students.add(studentService.getStudentBySno(questionStudentChooses.get(i).getSno()));
        }
        map.put("choices", questionStudentChooses);
        map.put("students", students);

        // 判断是否选择成功
        HttpSession session = request.getSession();
        session.setAttribute("snoSured", question.getSno());

        Object snoSured = session.getAttribute("snoSured");
        int snoSuredInt;
        if (snoSured != null) {
            snoSuredInt = (int) snoSured;
        } else {
            snoSuredInt = -1;
        }
        boolean isSured = false;
        if (snoSuredInt != -1 && snoSuredInt == question.getSno()) {
            isSured = true;
        }
        map.put("isSured", isSured);

        return "TeaQuesDetails";
    }


    @RequestMapping(value = "/TeaQuesDetailsMobile")
    public String TeaQuesDetailsMobile(HttpServletRequest request, @RequestParam("questionid") int questionid, Map<String, Object> map) {
        Question question = questionService.getSingleQuestionByQuestionid(questionid);
        map.put("question", question);
        List<QuestionStudentChoose> questionStudentChooses = questionStudentChooseService.getChoiceByQid(questionid);
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < questionStudentChooses.size(); i++) {
            students.add(studentService.getStudentBySno(questionStudentChooses.get(i).getSno()));
        }
        map.put("choices", questionStudentChooses);
        map.put("students", students);

        return "TeaQuesDetailsMobile";
    }

    @RequestMapping(value = "/sureQuesStu")
    public String sureQuesStu(HttpServletRequest request, @RequestParam("questionid") int questionid,
                              @RequestParam("sno") int sno, Map<String, Object> map) {
        boolean isSured = questionService.sureQuestionStudent(questionid, sno);
        map.put("isSured", isSured);
        HttpSession session = request.getSession();
        session.setAttribute("snoSured", sno);
        return "redirect:/TeaQuesDetails?questionid=" + questionid;
    }

    @RequestMapping(value = "/sureQuesStuMobile")
    public String sureQuesStuMobile(HttpServletRequest request, @RequestParam("questionid") int questionid,
                                    @RequestParam("sno") int sno, Map<String, Object> map) {
        boolean isSured = questionService.sureQuestionStudent(questionid, sno);
        map.put("isSured", isSured);
        return "redirect:/TeaQuesDetailsMobile?questionid=" + questionid;
    }
}