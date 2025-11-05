package edu.ynu.se.xiecheng.achitectureclass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchDTO{
    private Long id;
    private String batch;
    private String store;
    private String planttime;
    private String pickuptime;
    private String antipestrate;
    private String prate;
}
