package edu.ynu.se.xiecheng.achitectureclass.dao;

import edu.ynu.se.xiecheng.achitectureclass.common.dao.LogicDAO;
import edu.ynu.se.xiecheng.achitectureclass.entity.Greenhouse;

import java.util.List;

public interface GreenhouseDao extends LogicDAO<Greenhouse,Long> {
    List<Greenhouse> getGreenhousesByIdNotNull();
}