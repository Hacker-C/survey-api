package com.cg.controller;

import com.cg.pojo.vo.AnswerVo;
import com.cg.result.Result;
import com.cg.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;


    @PostMapping
    public Result saveAnswer(@RequestBody AnswerVo answerVo) {
        return answerService.saveAnswer(answerVo);
    }

    @GetMapping("{id}")
    public Result analysisAnswer(@PathVariable(value = "id") Integer surveyId) {
        return answerService.analysisAnswer(surveyId);
    }
}
