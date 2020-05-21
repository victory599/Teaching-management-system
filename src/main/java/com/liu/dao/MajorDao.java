package com.liu.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 专业相关
 */
@Repository
public interface MajorDao {
    /**
     * 查询学院下的所有专业名
     * @param collegeName
     * @return
     */
    public List<String> getMajorNamesByCollegeName(String collegeName);

    /**
     * 通过专业名查询专业id
     * @param majorName
     * @return
     */
    public Integer getMajorIdByMajorName(String majorName);
}
