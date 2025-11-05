package edu.ynu.se.xiecheng.achitectureclass.entity;

import edu.ynu.se.xiecheng.achitectureclass.common.entity.LogicEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance
public class Accesstoken extends LogicEntity {
    @Column(updatable = false, insertable = false)
    protected Long id;

    @Column
    protected String token;

    @Column
    protected String expires;

    @Column
    protected String valid;

    public Accesstoken(String token, String expires, String valid) {
        this.id = null;
        this.token = token;
        this.expires = expires;
        this.valid = valid;
    }

    public Accesstoken() {

    }
}
