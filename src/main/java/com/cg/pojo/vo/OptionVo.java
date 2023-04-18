package com.cg.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel
public class OptionVo {
    private Integer questionId;
    private String content;
    private Integer sort;

}