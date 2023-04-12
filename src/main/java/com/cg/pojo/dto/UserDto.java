package com.cg.pojo.dto;

import lombok.Data;

@Data
public class UserDto {
    private String nickname;
    private String avatar;
    private Integer gender;
    private String email;
    private String phone;
}
