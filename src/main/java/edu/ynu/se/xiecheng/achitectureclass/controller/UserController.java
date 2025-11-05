package edu.ynu.se.xiecheng.achitectureclass.controller;

import edu.ynu.se.xiecheng.achitectureclass.dao.UserDao;
import edu.ynu.se.xiecheng.achitectureclass.entity.User;
import edu.ynu.se.xiecheng.achitectureclass.service.UserService;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户的控制器")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController{

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        User user = userDao.findByName(username);
        if (user == null) {
            return "error";
        }
        if (!user.getPassword().equals(password)) {
            return "error";
        }
        User tuser = new User(user.getId(), user.getName(), user.getPassword(),user.getEmail(),user.getPhone(),user.getRole());

        return userService.login(tuser);
    }
    @PostMapping("/verifyToken")
    public String verifyToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        if (token != null && !token.isEmpty()) {
            return userService.verifyToken(token);
        } else {
            return "2"; //无效
        }
    }
}
@Getter
@Setter
class LoginRequest {
    private String username;
    private String password;
}
@Getter
@Setter
class TokenRequest {
    private String token;
}

