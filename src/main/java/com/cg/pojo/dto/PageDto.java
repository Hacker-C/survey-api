package com.cg.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private List<T> rows;
    private Long total;
}
