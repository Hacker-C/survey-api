package com.cg.pojo.dto;

import lombok.Data;

import java.util.Date;


@Data
public class SurveyDto {
    private Integer id;
    private String title;
    private Integer status;
    private String description;
    private Date expireTime;
    private Date createTime;
}