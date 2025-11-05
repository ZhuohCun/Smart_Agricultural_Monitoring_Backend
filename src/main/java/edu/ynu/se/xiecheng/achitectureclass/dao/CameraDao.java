package edu.ynu.se.xiecheng.achitectureclass.dao;

import edu.ynu.se.xiecheng.achitectureclass.common.dao.LogicDAO;
import edu.ynu.se.xiecheng.achitectureclass.entity.Camera;

import java.util.List;

public interface CameraDao extends LogicDAO<Camera, Long> {
        List<Camera> getCamerasById$greenhouseIs(String id$greenhouse);
}