package com.cg.controller;

import com.cg.pojo.Answer;
import com.cg.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;


    @GetMapping
    public List<Answer> getUserList() {
        return answerService.list();
    }
}
