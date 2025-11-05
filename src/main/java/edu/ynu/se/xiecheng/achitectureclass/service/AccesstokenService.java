package edu.ynu.se.xiecheng.achitectureclass.service;
import edu.ynu.se.xiecheng.achitectureclass.common.service.LogicService;
import edu.ynu.se.xiecheng.achitectureclass.dao.AccesstokenDao;
import com.google.gson.Gson;
import edu.ynu.se.xiecheng.achitectureclass.dto.AccesstokenDTO;
import lombok.Getter;
import lombok.Setter;
import edu.ynu.se.xiecheng.achitectureclass.entity.Accesstoken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import okhttp3.*;
import java.io.IOException;



@Service
public class AccesstokenService extends LogicService<AccesstokenDao,Accesstoken,Long> {
    @Autowired
    private AccesstokenDao accesstokenDao;
    public AccesstokenService(AccesstokenDao lr) {
        super(lr);
    }
    public String updateToken() throws IOException {
        Accesstoken accesstoken = accesstokenDao.findByValid("1");
        if (accesstoken!=null) {  //数据库存在token
            if(!this.isTokenExpired(accesstoken.getExpires())){   //token未过期
                return accesstoken.getToken();
            }else {   //token过期了
                responsebody newtokenbody=getToken();
                long expire = System.currentTimeMillis() + Long.parseLong(newtokenbody.getExpires_in())*1000L;
                Accesstoken newtoken=new Accesstoken(newtokenbody.getAccess_token(),String.valueOf(expire) ,"1");
                synchronized (this){
                    accesstoken.setValid("0");
                    accesstokenDao.setValid0();
                }
                accesstokenDao.save(newtoken);
                return newtoken.getToken();
            }
        }else{  //数据库里token不存在
            responsebody newtokenbody=getToken();
            long expire = System.currentTimeMillis() + Long.parseLong(newtokenbody.getExpires_in())*1000L;
            Accesstoken newtoken=new Accesstoken(newtokenbody.getAccess_token(),String.valueOf(expire),"1");
            synchronized (this){
                accesstokenDao.setValid0();
            }
            accesstokenDao.save(newtoken);
            return newtoken.getToken();
        }
    }
    public responsebody getToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        FormBody requestBody = new FormBody.Builder()
                .add("scope", "fbox")
                .add("client_id", "68db2e8bda8d47b5b5db7eaf71c7dcdd")
                .add("client_secret", "76dc724c95004acab25482d344dab407")
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url("https://fbox360.com/idserver/core/connect/token")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseBody = response.body().string();
                Gson gson = new Gson();
                responsebody res = gson.fromJson(responseBody, responsebody.class);
                return res;
            } else {
                return null;
            }
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
class responsebody{
    private String access_token;
    private String expires_in;
    private String token_type;
    private String scope;
}