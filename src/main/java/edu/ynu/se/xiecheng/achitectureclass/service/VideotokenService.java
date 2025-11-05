package edu.ynu.se.xiecheng.achitectureclass.service;
import edu.ynu.se.xiecheng.achitectureclass.common.service.LogicService;
import edu.ynu.se.xiecheng.achitectureclass.dao.VideotokenDao;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import edu.ynu.se.xiecheng.achitectureclass.entity.Videotoken;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import okhttp3.*;
import java.io.IOException;


@Service
public class VideotokenService extends LogicService<VideotokenDao,Videotoken,Long> {
    @Autowired
    private VideotokenDao videotokenDao;
    public VideotokenService(VideotokenDao lr) {
        super(lr);
    }
    public String updateToken() throws IOException {
        Videotoken videotoken = videotokenDao.findByValid("1");
        if (videotoken!=null) {  //数据库存在token
            if(!this.isTokenExpired(videotoken.getExpires())){   //token未过期
                return videotoken.getToken();
            }else {   //token过期了
                vtresponsebody newtokenbody=getToken();
                Videotoken newtoken=new Videotoken(newtokenbody.getData().getAccessToken(),String.valueOf(newtokenbody.getData().getExpiresTime()) ,"1");
                synchronized (this){
                    videotoken.setValid("0");
                    videotokenDao.setValid0();
                }
                videotokenDao.save(newtoken);
                return newtoken.getToken();
            }
        }else{  //数据库里token不存在
            vtresponsebody newtokenbody=getToken();
            Videotoken newtoken=new Videotoken(newtokenbody.getData().getAccessToken(),String.valueOf(newtokenbody.getData().getExpiresTime()) ,"1");
            synchronized (this){
                videotokenDao.setValid0();
            }
            videotokenDao.save(newtoken);
            return newtoken.getToken();
        }
    }
    public vtresponsebody getToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        json.put("accountId", "1ca7f0ab24ba41b291346347ec30140e");
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url("http://42.193.14.241:7000/ysapi/subAccount/getToken")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            Gson gson = new Gson();
            vtresponsebody item = gson.fromJson(responseBody, new com.google.gson.reflect.TypeToken<vtresponsebody>(){}.getType());
            return item;
        } else {
            System.out.println("Request failed: " + response.code());  //弹出提示供后台参考是否有更新失败现象
            return null;
        }
    }
    public boolean isTokenExpired(String expireTime) {

        long expireTimestamp = Long.parseLong(expireTime);
        long currentTime = System.currentTimeMillis();
        return currentTime >= expireTimestamp;
    }
}
@Getter
@Setter
class vtresponsebody{
    private String result;
    private vtdata data;
    private String page;
    private String code;
    private String msg;
}
@Getter
class vtdata{
    private String accessToken;
    private String expiresTime;
}