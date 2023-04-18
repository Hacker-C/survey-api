package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel
@Accessors(chain = true)
public class OptionDto2 {
    private String content;
    private Integer number;
    private Integer percent;
}
