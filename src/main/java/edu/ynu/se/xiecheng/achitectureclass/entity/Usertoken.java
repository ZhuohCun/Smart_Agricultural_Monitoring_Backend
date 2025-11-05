package edu.ynu.se.xiecheng.achitectureclass.entity;

import lombok.Getter;

@Getter
public class Usertoken{
    String ghid;
    String token;
    public Usertoken(String ghid, String token){
        this.ghid = ghid;
        this.token = token;
    }
}
