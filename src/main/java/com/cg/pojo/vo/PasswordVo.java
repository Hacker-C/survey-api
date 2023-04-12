package com.cg.pojo.vo;

import lombok.Data;

@Data

public class PasswordVo {
    private String oldPassword;
    private String newPassword;
    private String rePassword;
}
