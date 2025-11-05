package edu.ynu.se.xiecheng.achitectureclass.entity;

import edu.ynu.se.xiecheng.achitectureclass.common.entity.LogicEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance
public class Camera extends LogicEntity {
    @Column(updatable = false, insertable = false)
    protected Long id;

    @Column
    protected String serialNumber;

    @Column
    protected String url;

    @Column
    protected String id$greenhouse;

}
