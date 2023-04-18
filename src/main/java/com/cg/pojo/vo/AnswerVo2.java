package com.cg.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class AnswerVo2 {
    private Integer optionId;
    private Integer questionId;
    private String content;
}
