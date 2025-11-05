package edu.ynu.se.xiecheng.achitectureclass.common.service;

import edu.ynu.se.xiecheng.achitectureclass.common.dao.LogicDAO;
import edu.ynu.se.xiecheng.achitectureclass.common.entity.LogicEntity;
import edu.ynu.se.xiecheng.achitectureclass.common.utils.UpdateTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public class LogicService<D extends LogicDAO<T,ID>,T extends LogicEntity, ID extends Serializable>{

    protected D dao;
    public LogicService(D lr){
        this.dao = lr;
    }
    protected D getDAO(){
        return dao;
    }

    public T  GET(ID id){
        return  dao.getReferenceById(id);
    }
    public List<T> getAll(){
       return dao.findAll();
    }
    /** 基础的分页查询
     * @param page 第几页
     * @param size 每页的大小
     * @return 返回一个分页集合  */
    public Page<T> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createTime").descending());
        return dao.findAll(pageable);
    }
    public T  PUT(T entity){
        T sourceEntity = dao.getReferenceById((ID)entity.getId());
        System.out.println("ID:"+sourceEntity.getId());
        /**
         * 支持部分字段更新
         */
        UpdateTool.copyNullProperties(sourceEntity, entity);
       return dao.save(entity);
    }
    public T  POST(T  entity){
        return dao.save(entity);
    }
    public void DELETE(T entity){
        dao.delete(entity);
    }
    public void DELETE(ID id){
        dao.deleteById(id);
    }
}
