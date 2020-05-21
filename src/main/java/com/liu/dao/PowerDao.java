package com.liu.dao;

import com.liu.entity.Power;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author 孟庆强
 */
@Repository
public interface PowerDao {
    public List<Power> getPower();

    public void deletePower();

    public void updatePower(Power power);

    public void insertPower(Power power);
}
