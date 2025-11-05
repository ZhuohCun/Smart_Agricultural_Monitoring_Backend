package edu.ynu.se.xiecheng.achitectureclass.service;

import com.google.gson.Gson;
import edu.ynu.se.xiecheng.achitectureclass.common.service.LogicService;
import edu.ynu.se.xiecheng.achitectureclass.dao.CameraDao;

import edu.ynu.se.xiecheng.achitectureclass.entity.Camera;
import lombok.Getter;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class CameraService extends LogicService<CameraDao,Camera,Long> {
    @Autowired
    private CameraDao cameraDao;
    @Autowired
    private VideotokenService videotokenService;
    public CameraService(@Autowired CameraDao dao) {
        super(dao);
    }
    public String getSn(String ghid){
        List<Camera> cameras = cameraDao.getCamerasById$greenhouseIs(ghid);
        return cameras.get(0).getSerialNumber();  //假设每个大棚只有一个摄像机
    }
    public String geturl(String ghid){
        List<Camera> cameras = cameraDao.getCamerasById$greenhouseIs(ghid);
        return cameras.get(0).getUrl();  //假设每个大棚只有一个摄像机
    }
    public String shot(String ghid,String quality) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String vtoken= videotokenService.updateToken();
        FormBody requestBody = new FormBody.Builder()
                .add("accessToken", vtoken)
                .add("deviceSerial", this.getSn(ghid))
                .add("channelNo", "1")
                .add("quality", quality)
                .build();

        Request request = new Request.Builder()
                .url("https://open.ys7.com/api/lapp/device/capture")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseBody = response.body().string();
                Gson gson = new Gson();
                shotresponse res = gson.fromJson(responseBody, shotresponse.class);
                return res.getData().getPicUrl();
            } else {
                return null;
            }
        }
    }
}

@Getter
class shotresponse{
    String msg;
    String code;
    shotdata data;
}
@Getter
class shotdata{
    String picUrl;
}