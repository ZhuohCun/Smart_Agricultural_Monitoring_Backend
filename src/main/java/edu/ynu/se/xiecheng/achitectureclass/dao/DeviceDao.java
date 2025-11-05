package edu.ynu.se.xiecheng.achitectureclass.dao;

import edu.ynu.se.xiecheng.achitectureclass.common.dao.LogicDAO;
import edu.ynu.se.xiecheng.achitectureclass.entity.Device;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface DeviceDao extends LogicDAO<Device,Long>{
    @Modifying
    @Transactional
    @Query("UPDATE Device SET currentvalue = :value WHERE id = :id")
    void updatecvalue(String value, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Device SET percent = :percent, ccondition= :ccondition, color= :color WHERE id = :id")
    void updatePCnC(String percent,String ccondition, String color, Long id);

    List<Device> getDevicesById$Greenhouse(String id$Greenhouse);
}