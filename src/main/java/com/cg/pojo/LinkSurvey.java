package com.cg.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @TableName link_survey
 */
@TableName(value ="link_survey")
@Data
@Accessors(chain = true)
public class LinkSurvey implements Serializable {
    private Integer id;

    private String link;

    private Integer surveyId;

    private Integer status;

    private static final long serialVersionUID = 1L;
}