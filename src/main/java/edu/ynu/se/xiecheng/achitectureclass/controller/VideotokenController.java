package edu.ynu.se.xiecheng.achitectureclass.controller;

import edu.ynu.se.xiecheng.achitectureclass.common.controller.LogicController;
import edu.ynu.se.xiecheng.achitectureclass.dao.VideotokenDao;
import edu.ynu.se.xiecheng.achitectureclass.entity.Usertoken;
import edu.ynu.se.xiecheng.achitectureclass.entity.Videotoken;
import edu.ynu.se.xiecheng.achitectureclass.service.UserService;
import edu.ynu.se.xiecheng.achitectureclass.service.VideotokenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = "视频令牌的控制器")
@RestController
@RequestMapping("/api/videotoken")
@CrossOrigin(origins = "*")
public class VideotokenController extends LogicController<VideotokenService, VideotokenDao,Videotoken,Long> {
    public VideotokenController(@Autowired VideotokenService service){
        super(service);
    }
    @Autowired
    private VideotokenService videotokenservice;
    @Autowired
    private UserService userService;
    @PostMapping("/gettoken")
    public String getAccesstoken(@RequestBody Usertoken usertoken) throws IOException {
        if(userService.verifyToken(usertoken.getToken())=="0"){
            return videotokenservice.updateToken();
        }
        return null;
    }
}
