package com.cg.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class SurveyVo{
    private String title;
    private String description;
    private LocalDateTime expireTime;
}