package edu.ynu.se.xiecheng.achitectureclass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideotokenDTO{
    private Long id;
    private String token;
    private String expires;
    private String valid;

    public VideotokenDTO(String token, String expires, String valid){
        this.id=null;
        this.token=token;
        this.expires=expires;
        this.valid=valid;
    }

}