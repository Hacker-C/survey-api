package com.cg.pojo.vo;

import lombok.Data;


@Data
public class QuestionVo {
    private Integer surveyId;
    private String title;
    private Integer type;
    private Integer required;

}