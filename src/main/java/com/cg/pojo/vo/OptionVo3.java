package com.cg.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class OptionVo3 {
    private Integer pageNum;
    private Integer pageSize;
    private Integer surveyId;
    private Integer questionId;
}
