package edu.ynu.se.xiecheng.achitectureclass.controller;

import edu.ynu.se.xiecheng.achitectureclass.common.controller.LogicController;
import edu.ynu.se.xiecheng.achitectureclass.dao.CameraDao;
import edu.ynu.se.xiecheng.achitectureclass.entity.Camera;
import edu.ynu.se.xiecheng.achitectureclass.entity.Usertoken;
import edu.ynu.se.xiecheng.achitectureclass.service.*;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = "摄像机实体的控制器")
@RestController
@RequestMapping("/api/camera")
@CrossOrigin(origins = "*")
public class CameraController extends LogicController<CameraService, CameraDao,Camera,Long> {
    @Autowired
    private CameraService cameraService;
    @Autowired
    private UserService userService;

    public CameraController(@Autowired CameraService service){
        super(service);
    }


    @PostMapping("/geturl")
    public String geturl(@RequestBody Usertoken usertoken) {
        if(userService.verifyToken(usertoken.getToken())=="0"){
            return cameraService.geturl(usertoken.getGhid());
        }else {
            return null;
        }
    }

    @PostMapping("/shot")
    public String shot(@RequestBody Shotreq shotreq) throws IOException {
        if(userService.verifyToken(shotreq.getToken())=="0"){
            return cameraService.shot(shotreq.getGhid(),shotreq.getQuality());
        }else {
            return null;
        }
    }
}
@Getter
class Shotreq {
    String ghid;
    String token;
    String quality;
}
