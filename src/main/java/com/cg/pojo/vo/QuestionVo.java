package com.cg.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel
public class QuestionVo {
    private Integer surveyId;
    private String title;
    private Integer type;
    private Integer required;

}