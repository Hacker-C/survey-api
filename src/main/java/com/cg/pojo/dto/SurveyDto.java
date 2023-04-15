package com.cg.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


@Data
public class SurveyDto {
    private Integer id;
    private String title;
    private Integer status;
    private String description;
    private LocalDateTime expireTime;
    private LocalDateTime createTime;
}