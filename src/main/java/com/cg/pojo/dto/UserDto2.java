package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class UserDto2 {
    private Long id;
    private String nickname;

    private Integer status;

    private String avatar;

    private Integer gender;

    private String email;

    private String phone;
}
