package edu.ynu.se.xiecheng.achitectureclass.controller;

import edu.ynu.se.xiecheng.achitectureclass.common.controller.LogicController;
import edu.ynu.se.xiecheng.achitectureclass.dao.BatchDao;
import edu.ynu.se.xiecheng.achitectureclass.entity.Batch;
import edu.ynu.se.xiecheng.achitectureclass.entity.Usertoken;
import edu.ynu.se.xiecheng.achitectureclass.service.BatchService;
import edu.ynu.se.xiecheng.achitectureclass.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "批次实体的控制器")
@RestController
@RequestMapping("/api/batch")
@CrossOrigin(origins = "*")
public class BatchController extends LogicController<BatchService, BatchDao,Batch,Long> {
    public BatchController(@Autowired BatchService service){
        super(service);
    }
    @Autowired
    private BatchDao batchDao;
    @Autowired
    private UserService userService;
    @PostMapping("/getInfo")
    public List<Batch> getInfo(@RequestBody Usertoken usertoken){
        if(userService.verifyToken(usertoken.getToken())=="0"){
            return batchDao.getBatchesByIdNotNull();
        }
        return null;  //token验证错误
    }
}
