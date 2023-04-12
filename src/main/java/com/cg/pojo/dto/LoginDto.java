package com.cg.pojo.dto;

import lombok.Data;

@Data
public class LoginDto {

    private Long id;
    private String nickname;
    private Integer role;
    private String avatar;
    private String token;
}
