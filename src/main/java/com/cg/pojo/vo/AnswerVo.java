package com.cg.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class AnswerVo {
    private Integer surveyId;
    private List<AnswerVo2> answers;
}
