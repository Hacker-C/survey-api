package com.cg.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class QuestionDto {
    private Integer id;

    private String title;

    private Integer type;

    private Integer required;

    private LocalDateTime createTime;

}