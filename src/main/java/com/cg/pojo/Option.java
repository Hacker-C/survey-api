package com.cg.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName t_option
 */
@TableName(value ="t_option")
@Data
public class Option implements Serializable {
    private Integer id;

    private Integer questionId;

    private String content;

    private Integer sort;

    private static final long serialVersionUID = 1L;
}