package edu.ynu.se.xiecheng.achitectureclass.dao;

import edu.ynu.se.xiecheng.achitectureclass.common.dao.LogicDAO;
import edu.ynu.se.xiecheng.achitectureclass.entity.Batch;

import java.util.List;

public interface BatchDao extends LogicDAO<Batch, Long> {
    List<Batch> getBatchesByIdNotNull();
}