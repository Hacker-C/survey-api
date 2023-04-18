package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class UserDto {
    private String nickname;
    private String avatar;
    private Integer gender;
    private String email;
    private String phone;
}
