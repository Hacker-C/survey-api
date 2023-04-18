package com.cg.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel
public class AnswerDto {
    private String  nickname;
    private String title;
    private String description;
    private LocalDateTime expireTime;
    private List<QuestionDto4> questions;
}
