package edu.ynu.se.xiecheng.achitectureclass.controller;

import edu.ynu.se.xiecheng.achitectureclass.common.controller.LogicController;
import edu.ynu.se.xiecheng.achitectureclass.dao.DeviceDao;
import edu.ynu.se.xiecheng.achitectureclass.entity.Device;
import edu.ynu.se.xiecheng.achitectureclass.entity.Usertoken;
import edu.ynu.se.xiecheng.achitectureclass.service.DeviceService;
import edu.ynu.se.xiecheng.achitectureclass.service.UserService;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "设备实体的控制器")
@RestController
@RequestMapping("/api/device")
@CrossOrigin(origins = "*")
public class DeviceController extends LogicController<DeviceService, DeviceDao,Device,Long>{

    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private UserService userService;
    public DeviceController(@Qualifier("deviceService") @Autowired DeviceService service){
        super(service);
    }

    @PostMapping("/getInfo")
    public List<deviceresponsebody> getInfo(@RequestBody Usertoken req){
        if(userService.verifyToken(req.getToken())=="0"){
            List<Device> devices = deviceDao.getDevicesById$Greenhouse(req.getGhid());
            List<deviceresponsebody> responselist = new ArrayList<>();
            devices.forEach(device -> {
                responselist.add(new deviceresponsebody(device.getName(),device.getCurrentvalue(),device.getUnit(),device.getPercent(),device.getType(),device.getCcondition(),device.getColor()));
            });
            return responselist;
        }else {
            return null;
        }
    }

}
@Getter
class deviceresponsebody{
    private String name;
    private String value;
    private String unit;
    private String percent;
    private String ccondition;
    private String type;  //0.土壤  1.环境
    private String color;

    public deviceresponsebody(String name, String value, String unit, String percent, String type,String ccondition,String color) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.percent = percent;
        this.type = type;
        this.ccondition=ccondition;
        this.color=color;
    }

    public deviceresponsebody() {
    }
}
