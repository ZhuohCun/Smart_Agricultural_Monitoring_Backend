package edu.ynu.se.xiecheng.achitectureclass.controller;

import edu.ynu.se.xiecheng.achitectureclass.common.controller.LogicController;
import edu.ynu.se.xiecheng.achitectureclass.dao.GreenhouseDao;
import edu.ynu.se.xiecheng.achitectureclass.entity.Greenhouse;
import edu.ynu.se.xiecheng.achitectureclass.entity.Usertoken;
import edu.ynu.se.xiecheng.achitectureclass.service.GreenhouseService;
import edu.ynu.se.xiecheng.achitectureclass.service.UserService;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "大棚实体的控制器")
@RestController
@RequestMapping("/api/greenhouse")
@CrossOrigin(origins = "*")
public class GreenhouseController extends LogicController<GreenhouseService, GreenhouseDao,Greenhouse,Long> {
    @Autowired
    private UserService userService;
    @Autowired
    private GreenhouseDao greenhouseDao;

    public GreenhouseController(@Autowired GreenhouseService service){
        super(service);
    }
    @PostMapping("/getInfo")
    public List<ghresponsebody> getInfo(@RequestBody Usertoken usertoken){
        if(userService.verifyToken(usertoken.getToken())=="0"){
            List<Greenhouse> greenhouseList=greenhouseDao.getGreenhousesByIdNotNull();
            List<ghresponsebody> responselist = new ArrayList<>();
            greenhouseList.forEach(greenhouse -> {
                responselist.add( new ghresponsebody(greenhouse.getId().toString(),greenhouse.getName(),greenhouse.getArea()));
            });
            return responselist;
        }
        return null;  //token验证错误
    }
}
@Getter
@Setter
class ghresponsebody{
    private String id;
    private String name;
    private String area;

    public ghresponsebody(String id, String name, String area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }
}
