package edu.ynu.se.xiecheng.achitectureclass.service;

import edu.ynu.se.xiecheng.achitectureclass.common.service.LogicService;
import edu.ynu.se.xiecheng.achitectureclass.dao.UserDao;
import edu.ynu.se.xiecheng.achitectureclass.common.JwtUtil;
import edu.ynu.se.xiecheng.achitectureclass.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService extends LogicService<UserDao,User,Long> {
    @Autowired
    private UserDao userDao;

    public UserService(UserDao lr) {
        super(lr);
    }

    public String login(User userDto) {
        User user = userDao.findByName(userDto.getName());
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        return JwtUtil.generateToken(user.getName(),43200000);
    }
    public String verifyToken(String token) {
        try {
            if (JwtUtil.validateToken(token)) {
                return "0"; //有效
            }
            return "1"; //过期
        } catch (Exception e) {
            return "2"; //无效
        }
    }
}