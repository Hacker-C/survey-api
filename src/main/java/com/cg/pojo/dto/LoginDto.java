package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class LoginDto {

    private Long id;
    private String nickname;
    private Integer role;
    private String avatar;
    private String token;
}
