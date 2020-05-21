package com.liu.service;

import com.liu.dao.*;
import com.liu.entity.Teacher;
import com.liu.utils.ResponseMessage;
import com.liu.views.MobileSchedulingView;
import com.liu.views.SchedulingCourseView;
import com.liu.views.TeaCourseView;

import com.liu.views.SchedulingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 刘权达
 * 数据表 scheduling 服务层
 * 未完成  高谦修改了一下 tno sno 的数据类型 String -> Integer
 */
@Service
public class SchedulingService {
    @Autowired
    SchedulingDao schedulingDao;
    @Autowired
    PlanningDao planningDao;
    @Autowired
    SemesterDao semesterDao;
    @Autowired
    MajorDao majorDao;
    @Autowired
    TeacherDao teacherDao;

    public ResponseMessage getCourse(Integer cno,String year,String semester,
                                     String majorName,Integer grade){
        Integer semesterId = semesterDao.getSemesterId(year,semester);
        Integer majorId = majorDao.getMajorIdByMajorName(majorName);
        //判断是否已排
        Boolean flag = planningDao.checkIfOpen(semesterId,majorId,grade,cno);
        SchedulingView schedulingView = new SchedulingView(null,null,null,null,null);
        if(flag){
            schedulingView=schedulingDao.getCourse(semesterId,cno);
        }
        ResponseMessage message=ResponseMessage.getMessage(schedulingView!=null,ResponseMessage.SUCCESS,
                "查找成功！", ResponseMessage.WRONG,"查找失败！");
        message.setData(schedulingView);
        return message;
    }


    public ResponseMessage addCourse(Integer cno,Integer tno,String year,String semester,
                                     String majorName,Integer grade,String status,


                                     String capacity1,String address,String time){
        Integer capacity=Integer.valueOf(capacity1);
        Integer semesterId = semesterDao.getSemesterId(year,semester);
        Integer majorId = majorDao.getMajorIdByMajorName(majorName);
        schedulingDao.deleteCourse(semesterId,cno);
        schedulingDao.addCourse(semesterId,cno,tno,status,capacity,address,grade,time);
        schedulingDao.deleteCourseMajor(semesterId,cno,majorId,grade);
        schedulingDao.addCourseMajor(semesterId,cno,majorId,grade);
        planningDao.changeIfOpen(semesterId,majorId,grade,cno,true);
        SchedulingView schedulingView=schedulingDao.getCourse(semesterId,cno);
        ResponseMessage message=ResponseMessage.getMessage(schedulingView!=null,ResponseMessage.SUCCESS,
                "插入成功！", ResponseMessage.WRONG,"插入失败！");
        message.setData(schedulingView);
        return message;
    }

    public ResponseMessage deleteCourse(Integer cno,String year,String semester,
                                        String majorName,Integer grade){

        Integer semesterId = semesterDao.getSemesterId(year,semester);
        Integer majorId = majorDao.getMajorIdByMajorName(majorName);
        Integer count1 = schedulingDao.getCourseMajorCount(semesterId,cno);
        schedulingDao.deleteCourseMajor(semesterId,cno,majorId,grade);
        planningDao.changeIfOpen(semesterId,majorId,grade,cno,false);
        Integer count2 = schedulingDao.getCourseMajorCount(semesterId,cno);
        if(count2==0){
            schedulingDao.deleteCourse(semesterId,cno);
        }
        Boolean flag=false;
        if(count1!=count2){
            flag=true;
        }
        ResponseMessage message=ResponseMessage.getMessage(flag!=false,ResponseMessage.SUCCESS,
                " 删除成功！", ResponseMessage.WRONG,"删除失败！");
        message.setData(flag);
        return message;
    }

    public ResponseMessage getCoursesByTnoAndTnameAndAddress(Integer tno,String tname,String address){
        System.out.println(tname);
        List<MobileSchedulingView> mobileSchedulingView = schedulingDao.getCoursesByTnoAndTnameAndAddress(tno,
                tname,address);
        ResponseMessage message=ResponseMessage.getMessage(mobileSchedulingView!=null,ResponseMessage.SUCCESS,
                " 查找成功！", ResponseMessage.WRONG,"查找失败！");
        message.setData(mobileSchedulingView);
        return message;
    }

    public ResponseMessage getTeacherByTnoAndTname(Integer tno,String tname){
        List<Teacher> data = teacherDao.getTeacherByTnoAndTname(tno,tname);
        ResponseMessage message=ResponseMessage.getMessage(data!=null,ResponseMessage.SUCCESS,
                " 查找成功！", ResponseMessage.WRONG,"查找失败！");
        message.setData(data);
        return message;
    }

    public ResponseMessage getAllCourses(String year,String semester,String majorName,Integer grade){
        List<SchedulingCourseView> data = schedulingDao.getAllCourses(year,semester,majorName,grade);
        ResponseMessage message=ResponseMessage.getMessage(data!=null,ResponseMessage.SUCCESS,
                " 查找成功！", ResponseMessage.WRONG,"查找失败！");
        message.setData(data);
        return message;
    }

    /**
     * @author: yuzhongrui
     * @func: 拿到老师正在开的课程的数据
     * @param tno
     * @return
     */
    public List<TeaCourseView> getCourseInfoByTno(int tno){
        List<TeaCourseView> teaCourseViews = schedulingDao.getCourseInfoByTno(tno);
        String courseTime;
        String[] handleCourseTime;

        for(int i=0;i<teaCourseViews.size();i++){
            String finalCourseTime="";//修正之后的课程时间表示
            courseTime = teaCourseViews.get(i).getCourseTime();
            handleCourseTime =  courseTime.split(";");
            for(int j=0;j<handleCourseTime.length;j++){
                StringBuffer stringBuilder = new StringBuffer(handleCourseTime[j]);
                for(int k=0;k<stringBuilder.length();k++){
                    if(stringBuilder.charAt(k)=='('){
                        stringBuilder.insert(k+1,"周数: ");
                    }else if (stringBuilder.charAt(k)==',' && stringBuilder.charAt(k+2)==','){
                        stringBuilder.insert(k+1,"星期: ");
                    }else if (stringBuilder.charAt(k)==','){//可能有bug
                        stringBuilder.insert(k+1,"节数: ");
                    }
                }
//                handleCourseTime[j] = stringBuilder.toString();
                finalCourseTime += stringBuilder.toString();
//                System.out.println(handleCourseTime[j]);
            }
            teaCourseViews.get(i).setCourseTime(finalCourseTime);
//            System.out.println(teaCourseViews.get(i).getCourseTime());
        }
        return teaCourseViews;
    }

}
