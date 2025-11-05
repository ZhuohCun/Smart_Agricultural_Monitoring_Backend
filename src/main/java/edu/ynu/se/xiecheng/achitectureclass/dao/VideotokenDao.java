package edu.ynu.se.xiecheng.achitectureclass.dao;

import edu.ynu.se.xiecheng.achitectureclass.common.dao.LogicDAO;
import edu.ynu.se.xiecheng.achitectureclass.dto.VideotokenDTO;
import edu.ynu.se.xiecheng.achitectureclass.entity.Videotoken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface VideotokenDao extends LogicDAO<Videotoken,Long> {
    Videotoken findByValid(String name);
    @Modifying
    @Transactional
    @Query("UPDATE Videotoken SET valid = '0' WHERE valid = '1'")
    void setValid0();
    void save(VideotokenDTO videotokenDTO);
}