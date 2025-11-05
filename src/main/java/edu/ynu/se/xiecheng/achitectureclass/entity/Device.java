package edu.ynu.se.xiecheng.achitectureclass.entity;

import edu.ynu.se.xiecheng.achitectureclass.common.entity.LogicEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance
public class Device extends LogicEntity {
    @Column(updatable = false, insertable = false)
    protected Long id;

    @Column
    protected String name;

    @Column
    protected String unit;

    @Column
    protected String id$Greenhouse;

    @Column
    protected String type;

    @Column
    protected String currentvalue;

    @Column
    protected String percent;

    @Column
    protected String ccondition;

    @Column
    protected String maxvalue;

    @Column
    protected String color;
}
