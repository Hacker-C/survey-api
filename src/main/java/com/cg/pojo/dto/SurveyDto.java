package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@ApiModel
public class SurveyDto {
    private Integer id;
    private String title;
    private Integer status;
    private Integer isLike;
    private String description;
    private LocalDateTime expireTime;
    private LocalDateTime createTime;
}