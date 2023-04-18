package com.cg.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class LoginVo {
    private String username;
    private String password;
    private String rePassword;
}
