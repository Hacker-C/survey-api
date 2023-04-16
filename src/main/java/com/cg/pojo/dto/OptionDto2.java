package com.cg.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OptionDto2 {
    private String content;
    private Integer number;
    private Integer percent;
}
