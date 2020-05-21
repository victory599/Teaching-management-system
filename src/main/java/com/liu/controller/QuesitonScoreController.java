package com.liu.controller;

import com.liu.entity.Question;
import com.liu.entity.QuestionScore;
import com.liu.entity.QuestionStudentChoose;
import com.liu.entity.User;
import com.liu.service.QuestionService;
import com.liu.service.QuestionStudentChooseService;
import com.liu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.liu.service.QuestionScoreService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class QuesitonScoreController {
    @Autowired
    QuestionScoreService questionScoreService;
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionStudentChooseService questionStudentChooseService;
    @Autowired
    StudentService studentService;
    @RequestMapping(value = "/StuScore")
    public String StuScore(HttpServletRequest request,
                           Map<String,Object> map){
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        int sno = ((User)user).getAccount();
        QuestionScore questionScore = questionScoreService.getQuestionScoreBySno(sno);

        double total = 0;

        if(questionScore==null)
            total = -1;
        else{
            total =  questionScore.getEarlyperformance()*0.1+questionScore.getMidexam()*0.2+questionScore.getThesisanswer()*0.3+questionScore.getPaper()*0.3+questionScore.getExtracredit()*0.1;
        }

        map.put("total",total);
        map.put("quesScore",questionScore);

        return "StuScore";
    }

    @RequestMapping(value = "/StuScoreMobile")
    public String StuScoreMobile(HttpServletRequest request,
                           Map<String,Object> map){
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        int sno = ((User)user).getAccount();
        QuestionScore questionScore = questionScoreService.getQuestionScoreBySno(sno);

        double total = 0;

        if(questionScore==null)
            total = -1;
        else{
            total =  questionScore.getEarlyperformance()*0.1+questionScore.getMidexam()*0.2+questionScore.getThesisanswer()*0.3+questionScore.getPaper()*0.3+questionScore.getExtracredit()*0.1;
        }

        map.put("total",total);
        map.put("quesScore",questionScore);

        return "StuScoreMobile";
    }

    @RequestMapping(value =  "/TeaAddScore")
    public String TeaAddScore(HttpServletRequest request,Map<String,Object>map){
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        int tno = ((User)user).getAccount();
        List<Question> questions = questionService.getQuestionByTno(tno);
        List<QuestionStudentChoose> questionStudentChooses = new ArrayList<>();
        for(int i=0;i<questions.size();i++){
            if (questions.get(i).getSno()!=-1){
                questionStudentChooses.add(new QuestionStudentChoose(questions.get(i).getQuestionid(),questions.get(i).getSno() ) );
            }
        }
        map.put("choices",questionStudentChooses);

        //判断是否打分成功
        int isJudged = -1;
        Object judgeObject = session.getAttribute("judge");
        Object hasChangedScoreObject = session.getAttribute("hasChangedScore");

        if (hasChangedScoreObject == null || judgeObject==null){
            isJudged = -1;
        }else if((boolean)hasChangedScoreObject==true){
            if((boolean)judgeObject==true){
                isJudged = 1;//打分成功
            }else {
                isJudged = 0;//已经打过分
            }
            session.removeAttribute("hasChangedScore");
        }

        map.put("judge",isJudged);
        return "TeaAddScore";
    }

    @RequestMapping(value =  "/TeaAddScoreMobile")
    public String TeaAddScoreMobile(HttpServletRequest request,Map<String,Object>map){
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        int tno = ((User)user).getAccount();
        List<Question> questions = questionService.getQuestionByTno(tno);
        List<QuestionStudentChoose> questionStudentChooses = new ArrayList<>();
        for(int i=0;i<questions.size();i++){
            if (questions.get(i).getSno()!=-1){
                questionStudentChooses.add(new QuestionStudentChoose(questions.get(i).getQuestionid(),questions.get(i).getSno() ) );
            }
        }
        map.put("choices",questionStudentChooses);
        return "TeaAddScoreMobile";
    }

    @RequestMapping(value = "/TeaAddScore",method = RequestMethod.POST)
    public String TeaAddScore(HttpServletRequest request,
                             @RequestParam("sno")int sno,
                             @RequestParam("questionid")int questionid,
                             @RequestParam("earlyperformance")int earlyperformance,
                             @RequestParam("midexam")int midexam,
                             @RequestParam("thesisanswer")int thesisanswer,
                             @RequestParam("paper")int paper,
                             @RequestParam("extracredit")int extracredit){
       HttpSession session = request.getSession();
        //判断有无打过分
        QuestionScore questionScoreCheck = questionScoreService.getQuestionScoreBySno(sno);
       if(questionScoreCheck!=null){
           session.setAttribute("judge",false);//表示已经打过分
           session.setAttribute("hasChangedScore",true);
           return "redirect:/TeaAddScore";
       }

       QuestionScore questionScore = new QuestionScore();
       questionScore.setSno(sno);
       questionScore.setQuestionid(questionid);
       questionScore.setEarlyperformance(earlyperformance);
       questionScore.setMidexam(midexam);
       questionScore.setThesisanswer(thesisanswer);
       questionScore.setPaper(paper);
       questionScore.setExtracredit(extracredit);
       questionScoreService.addQuestionScore(questionScore);
       session.setAttribute("hasChangedScore",true);
       session.setAttribute("judge",true);//表示打分成功
       return "redirect:/TeaAddScore";
    }

    @RequestMapping(value = "/TeaAddScoreMobile",method = RequestMethod.POST)
    public String TeaAddScoreMobile(HttpServletRequest request,
                              @RequestParam("sno")int sno,
                              @RequestParam("questionid")int questionid,
                              @RequestParam("earlyperformance")int earlyperformance,
                              @RequestParam("midexam")int midexam,
                              @RequestParam("thesisanswer")int thesisanswer,
                              @RequestParam("paper")int paper,
                              @RequestParam("extracredit")int extracredit){
        //判断有无打过分
        QuestionScore questionScoreCheck = questionScoreService.getQuestionScoreBySno(sno);
        if(questionScoreCheck!=null){
            return "redirect:/TeaAddScoreMobile";
        }

        QuestionScore questionScore = new QuestionScore();
        questionScore.setSno(sno);
        questionScore.setQuestionid(questionid);
        questionScore.setEarlyperformance(earlyperformance);
        questionScore.setMidexam(midexam);
        questionScore.setThesisanswer(thesisanswer);
        questionScore.setPaper(paper);
        questionScore.setExtracredit(extracredit);
        questionScoreService.addQuestionScore(questionScore);
        return "redirect:/TeaAddScoreMobile";
    }




    //Back

    @RequestMapping(value = "/ManageLookThroughGrade")
    public String ManageLookThroughGrade(HttpServletRequest request,
                                         Map<String,Object> map){
        List<QuestionScore> questionScores = questionScoreService.getAllQuestionScore();
        map.put("scoreInfos",questionScores);
        return "ManageLookThroughGrade";
    }


    @RequestMapping(value = "/ManageScore")
    public String ManageScore(HttpServletRequest request,
                              @RequestParam("sno")int sno,
                              Map<String,Object> map){
        HttpSession session = request.getSession();
        Object isChangedObject = session.getAttribute("gradeIsChanged");
        Object hasChangedObject = session.getAttribute("gradeHasChanged");
        Boolean hasChanged = (Boolean)hasChangedObject;

        if(isChangedObject == null || hasChangedObject==null){
            session.setAttribute("gradeIsChanged",false);
        }
        else{
            if(hasChanged==true){
                session.setAttribute("gradeHasChanged",false);
            }
            else {
                session.setAttribute("gradeIsChanged",false);
            }
        }

//        System.out.println(isChangedObject);
        QuestionScore questionScore = questionScoreService.getQuestionScoreBySno(sno);
        map.put("Score",questionScore);
        return "ManageScore";
    }

    @RequestMapping(value = "/ManageScore",method = RequestMethod.POST)
    public String ManageScore(HttpServletRequest request,
                              @RequestParam("sno")int sno,
                              @RequestParam("questionid")int questionid,
                              Map<String,Object> map,
                              @RequestParam("earlyperformance")int earlyperformance,
                              @RequestParam("midexam")int midexam,
                              @RequestParam("thesisanswer")int thesisanswer,
                              @RequestParam("paper")int paper,
                              @RequestParam("extracredit")int extracredit){
        QuestionScore questionScore = new QuestionScore();
        questionScore.setSno(sno);
        questionScore.setQuestionid(questionid);
        questionScore.setEarlyperformance(earlyperformance);
        questionScore.setMidexam(midexam);
        questionScore.setThesisanswer(thesisanswer);
        questionScore.setPaper(paper);
        questionScore.setExtracredit(extracredit);
        boolean isChanged = questionScoreService.changeQuestionScore(questionScore);
        HttpSession session = request.getSession();
        session.setAttribute("gradeIsChanged",isChanged);
        session.setAttribute("gradeHasChanged",true);
        return "redirect:ManageLookThroughGrade";//"redirect:/ManageScore"+"?sno="+sno;
    }


    //一下皆为测试接口
//    @GetMapping("addQC")
//    public void addQuestionScore(){
//        QuestionScore q = new QuestionScore(3333,2,3,4,5,6,7);
//        questionScoreService.addQuestionScore(q);
//    }
//
//    @GetMapping("getQSBS")
//    public void getQuestionScoreBySno(){
//        System.out.println(questionScoreService.getQuestionScoreBySno(3333));
//    }
//
//    @GetMapping("getQSBT")
//    public void getQuestionScoreByTno(){
//        System.out.println(questionScoreService.getQuestionScoreByTno(1));
//    }

}

