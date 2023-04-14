package com.cg.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class AnswerVo {
    private Integer surveyId;
    private List<AnswerVo2> answers;
}
