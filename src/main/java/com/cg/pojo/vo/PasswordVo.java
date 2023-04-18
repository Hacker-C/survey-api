package com.cg.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class PasswordVo {
    private String oldPassword;
    private String newPassword;
    private String rePassword;
}
