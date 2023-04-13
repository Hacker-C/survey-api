package com.cg.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName option
 */
@TableName(value ="option")
@Data
public class OptionDto implements Serializable {
    private Integer id;

    private String content;

    private Integer sort;

}