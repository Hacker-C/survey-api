package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PageDto<T> {
    private List<T> rows;
    private Long total;
}
