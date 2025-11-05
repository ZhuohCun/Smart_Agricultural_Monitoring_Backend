package edu.ynu.se.xiecheng.achitectureclass.service;

import edu.ynu.se.xiecheng.achitectureclass.common.service.LogicService;
import edu.ynu.se.xiecheng.achitectureclass.dao.GreenhouseDao;

import edu.ynu.se.xiecheng.achitectureclass.entity.Greenhouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GreenhouseService extends LogicService<GreenhouseDao,Greenhouse,Long> {
    public GreenhouseService(@Autowired GreenhouseDao dao) {
        super(dao);
    }

}
