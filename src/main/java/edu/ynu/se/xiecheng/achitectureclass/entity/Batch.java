package edu.ynu.se.xiecheng.achitectureclass.entity;

import edu.ynu.se.xiecheng.achitectureclass.common.entity.LogicEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance
public class Batch extends LogicEntity {
    @Column(updatable = false, insertable = false)
    protected Long id;

    @Column
    protected String batch;

    @Column
    protected String store;

    @Column
    protected String planttime;

    @Column
    protected String pickuptime;

    @Column
    protected String antipestrate;

    @Column
    protected String prate;


}
