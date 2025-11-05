package edu.ynu.se.xiecheng.achitectureclass.dao;

import edu.ynu.se.xiecheng.achitectureclass.common.dao.LogicDAO;
import edu.ynu.se.xiecheng.achitectureclass.dto.AccesstokenDTO;
import edu.ynu.se.xiecheng.achitectureclass.entity.Accesstoken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AccesstokenDao extends LogicDAO<Accesstoken, Long> {
    Accesstoken findByValid(String name);
    @Modifying
    @Transactional
    @Query("UPDATE Accesstoken SET valid = '0' WHERE valid = '1'")
    void setValid0();
    void save(AccesstokenDTO accesstokenDTO);
}