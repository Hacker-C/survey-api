package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@ApiModel
public class QuestionDto {
    private Integer id;

    private String title;

    private Integer type;

    private Integer required;

    private LocalDateTime createTime;



}