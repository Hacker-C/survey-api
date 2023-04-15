package com.cg.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName answer
 */
@TableName(value ="answer")
@Data
public class Answer implements Serializable {
    private Integer id;

    private Integer optionId;

    private Integer questionId;

    private Integer surveyId;

    private String content;

    private static final long serialVersionUID = 1L;
}