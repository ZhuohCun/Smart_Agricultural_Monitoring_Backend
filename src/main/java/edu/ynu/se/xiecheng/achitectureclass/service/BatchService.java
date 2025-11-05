package edu.ynu.se.xiecheng.achitectureclass.service;

import edu.ynu.se.xiecheng.achitectureclass.common.service.LogicService;
import edu.ynu.se.xiecheng.achitectureclass.dao.BatchDao;

import edu.ynu.se.xiecheng.achitectureclass.entity.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BatchService extends LogicService<BatchDao,Batch,Long> {
    public BatchService(@Autowired BatchDao dao) {
        super(dao);
    }
}