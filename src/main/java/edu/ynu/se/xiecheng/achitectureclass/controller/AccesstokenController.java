package edu.ynu.se.xiecheng.achitectureclass.controller;

import edu.ynu.se.xiecheng.achitectureclass.common.controller.LogicController;
import edu.ynu.se.xiecheng.achitectureclass.dao.AccesstokenDao;
import edu.ynu.se.xiecheng.achitectureclass.entity.Accesstoken;
import edu.ynu.se.xiecheng.achitectureclass.service.AccesstokenService;
import edu.ynu.se.xiecheng.achitectureclass.entity.Usertoken;
import edu.ynu.se.xiecheng.achitectureclass.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = "访问令牌实体的控制器")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AccesstokenController extends LogicController<AccesstokenService, AccesstokenDao,Accesstoken,Long> {
    @Autowired
    private AccesstokenService accesstokenservice;
    @Autowired
    private UserService userService;
    public AccesstokenController(@Autowired AccesstokenService service){
        super(service);
    }

    @PostMapping("/getaccesstoken")
    public ResponseEntity<?> getAccesstoken(@RequestBody Usertoken usertoken) throws IOException {
        if(userService.verifyToken(usertoken.getToken())=="0"){
            return ResponseEntity.ok(accesstokenservice.updateToken());
        }
        return null;
    }
}