package com.liu.service;

import com.liu.dao.CollegeDao;
import com.liu.entity.College;
import com.liu.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeService {
    @Autowired
    CollegeDao collegeDao;

    /**
     * 获取全部学院的功能
     * @return
     */
    public ResponseMessage getAllCollege() {
        List<College> data = collegeDao.getColleges();
        ResponseMessage message = ResponseMessage.getMessage(data.size() > 0, ResponseMessage.SUCCESS,
                "查询成功！", ResponseMessage.EmptyDate, "查询结果为空！");
        message.setData(data);
        return message;
    }

    /**
     * 根据学院id，来获取学院的其他信息
     * @param id 学院的id
     * @return
     */
    public ResponseMessage getCollegeById(String id) {
        College Co = collegeDao.getCollegeById(id);
        ResponseMessage message = ResponseMessage.getMessage(Co != null, ResponseMessage.SUCCESS,
                "查询成功！", ResponseMessage.EmptyDate, "查询结果为空！");
        message.setData(Co);
        return message;
    }

    /**
     * 获取所有学院名字
     * @return
     */
    public ResponseMessage getCollegeNames() {
        List<String> data = collegeDao.getCollegeNames();
        ResponseMessage message = ResponseMessage.getMessage(data != null, ResponseMessage.SUCCESS,
                "查询成功！", ResponseMessage.EmptyDate, "查询结果为空！");
        message.setData(data);
        return message;
    }

    /**
     * 根据学院id，来获取学院的名字
     * @param id 学院的id
     * @return
     */
    public String getColnameById(int id) {
        String colname = collegeDao.getColNameById(id);
        return colname;
    }
}