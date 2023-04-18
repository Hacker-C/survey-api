package com.cg.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@ApiModel
public class SurveyVo2 {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime expireTime;
}