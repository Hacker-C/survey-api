package com.cg.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class SurveyDto3 {
    private Integer id;
    private String  nickname;
    private String title;
    private String description;
    private LocalDateTime expireTime;
    private List<QuestionDto3> questions;
}