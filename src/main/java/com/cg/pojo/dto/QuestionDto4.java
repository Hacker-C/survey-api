package com.cg.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto4 {
    private String title;

    private Integer type;

    private Integer required;
    private List<OptionDto2> options;
}
