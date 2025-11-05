package edu.ynu.se.xiecheng.achitectureclass.common.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class LogicEntity extends BaseEntity{
    @Column(insertable = false)
    protected Date deletedTime;

    @Column(insertable = false,
            nullable = false,
            columnDefinition="INT default 0")
    protected Integer isDeleted;
}
