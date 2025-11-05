package edu.ynu.se.xiecheng.achitectureclass.dao;

import edu.ynu.se.xiecheng.achitectureclass.common.dao.LogicDAO;
import edu.ynu.se.xiecheng.achitectureclass.entity.User;

public interface UserDao extends LogicDAO<User,Long> {
    User findByName(String name);
}