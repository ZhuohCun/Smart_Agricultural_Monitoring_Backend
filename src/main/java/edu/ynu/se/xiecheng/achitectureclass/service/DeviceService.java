package edu.ynu.se.xiecheng.achitectureclass.service;

import com.google.gson.Gson;
import edu.ynu.se.xiecheng.achitectureclass.common.service.LogicService;
import edu.ynu.se.xiecheng.achitectureclass.dao.DeviceDao;
import edu.ynu.se.xiecheng.achitectureclass.dao.GreenhouseDao;
import edu.ynu.se.xiecheng.achitectureclass.entity.Device;
import edu.ynu.se.xiecheng.achitectureclass.entity.Greenhouse;
import lombok.Getter;
import okhttp3.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;

@Service
@EnableScheduling
public class DeviceService extends LogicService<DeviceDao, Device,Long> {
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private GreenhouseDao greenhouseDao;
    @Autowired
    private AccesstokenService accesstokenService;

    public DeviceService(@Qualifier("deviceDao") @Autowired DeviceDao dao) {
        super(dao);
    }
    public void updateinfo(String ghid,String atoken) throws JSONException, IOException {
        synchronized (this) {
            List<Device> devices = deviceDao.getDevicesById$Greenhouse(ghid);
            OkHttpClient client = new OkHttpClient();
            JSONObject json = new JSONObject();
            List<String> idlist = new ArrayList<String>();
            for (Device device : devices) {
                idlist.add(device.getId().toString());
            }
            json.put("ids", idlist);
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json.toString(), JSON);
            String url = "https://fbox360.com/api/v2/dmon/value/get?boxNo=" + ghid;
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer "+atoken)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                List<deviceresponsebody> items = gson.fromJson(responseBody, new com.google.gson.reflect.TypeToken<List<deviceresponsebody>>(){}.getType());
                for (deviceresponsebody item : items) {
                    if(item.getName().equals("空气温度") || item.getName().equals("空气相对湿度")) {
                        deviceDao.updatecvalue(String.valueOf(Float.parseFloat(item.getValue())/10), Long.valueOf(item.getId()));
                    }else {
                        deviceDao.updatecvalue(item.getValue(), Long.valueOf(item.getId()));
                    }
                }
            } else {
                System.out.println("Request failed: " + response.code());  //弹出提示供后台参考是否有更新失败现象
            }
        }
        this.updatedetails(ghid);
    }
    @Scheduled(fixedRate = 60000)  //一分钟更新一次数据
    public void scheduledupdate() {
        List<Greenhouse> ghlist = greenhouseDao.getGreenhousesByIdNotNull();
        ghlist.forEach(gh -> {
            String ghid = gh.getId().toString();
            String atoken = null;
            try {
                atoken = accesstokenService.updateToken();
                this.updateinfo(ghid, atoken);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void updatedetails(String ghid){
        List<Device> devices = deviceDao.getDevicesById$Greenhouse(ghid);
        for (Device device : devices) {
            Float percent = Float.parseFloat(device.getCurrentvalue())/Float.parseFloat(device.getMaxvalue());
            String condition;
            String color;
            if(percent<0.3){
                condition="低";
                color="darkblue";
            } else if (percent<=0.7) {
                condition="良";
                if(device.getType()=="1"){
                    color="green";
                }else {
                    color="darkcyan";
                }
            }else {
                condition="高";
                color="red";
            }
            deviceDao.updatePCnC(String.valueOf(percent),condition,color,device.getId());
        }
    }
}

@Getter
class deviceresponsebody {
    String id;
    String name;
    String value;
}