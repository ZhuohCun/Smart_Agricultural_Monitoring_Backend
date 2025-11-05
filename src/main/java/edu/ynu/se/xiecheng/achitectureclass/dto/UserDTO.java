package edu.ynu.se.xiecheng.achitectureclass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private Integer role;
    public UserDTO(Long id, String name, String password, String email, String phone, Integer role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}
