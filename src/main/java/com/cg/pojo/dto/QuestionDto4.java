package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class QuestionDto4 {
    private String title;

    private Integer type;

    private Integer required;
    private List<OptionDto2> options;
}
