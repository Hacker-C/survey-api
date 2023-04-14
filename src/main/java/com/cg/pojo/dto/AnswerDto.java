package com.cg.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnswerDto {
    private String  nickname;
    private String title;
    private String description;
    private LocalDateTime expireTime;
    private List<QuestionDto4> questions;
}
