package com.cg.pojo.dto;

import lombok.Data;

import java.util.List;


@Data
public class QuestionDto3 {
    private Integer id;

    private String title;

    private Integer type;

    private Integer required;

    private List<OptionDto> options;
}