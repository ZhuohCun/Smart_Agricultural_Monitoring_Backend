package edu.ynu.se.xiecheng.achitectureclass.common.controller;

import edu.ynu.se.xiecheng.achitectureclass.common.dao.LogicDAO;
import edu.ynu.se.xiecheng.achitectureclass.common.entity.LogicEntity;
import edu.ynu.se.xiecheng.achitectureclass.common.service.LogicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


@Api(tags ="基础Controller")
@CrossOrigin
public abstract class LogicController<S extends  LogicService<D,T,ID>, D extends LogicDAO<T,ID>,T extends LogicEntity, ID extends Serializable>{
    protected S service;
    protected S getService(){
        return service;
    }
    public LogicController(S ls){
        this.service = ls;
    }
    @GetMapping("/get")
    @ApiOperation("通过ID获取实体")
    public T GET(@RequestParam ID id){
        return service.GET(id);
    }
    @ApiOperation("获取所有实体")
    @GetMapping("/getall")
    public List<T> getAll(){
        return service.getAll();
    }
    @ApiOperation("分页获取所有实体")
    @GetMapping("/getallPage")
    public Page<T> getAll(@RequestParam int page, @RequestParam int size){
        return service.getAll(page,size);
    }
    @ApiOperation("修改实体")
    @PostMapping("/put")
    public T PUT(@RequestBody T entity){
        return service.PUT(entity);
    }

    @ApiOperation("创建实体")
    @PostMapping("/post")
    public T POST(@RequestBody T  entity){
        return service.POST(entity);
    }

    @ApiOperation("删除实体")
    @PostMapping("/delete")
    public void DELETE(@RequestBody T entity){
        service.DELETE(entity);
    }

    @ApiOperation("通过ID删除实体")
    @GetMapping("/delete")
    public void DELETE(@RequestParam ID id){
        service.DELETE(id);
    }
}
