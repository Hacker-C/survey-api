package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class OptionDto {
    private Integer id;

    private String content;

    private Integer sort;

}