package com.cg.controller;

import com.cg.pojo.vo.SurveyVo;
import com.cg.pojo.vo.SurveyVo2;
import com.cg.result.Result;
import com.cg.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    @PostMapping
    public Result saveSurvey(@RequestBody SurveyVo surveyVo) {
        return surveyService.saveSurvey(surveyVo);
    }
    @PutMapping
    public Result updateSurvey(@RequestBody SurveyVo2 surveyVo2) {
        return surveyService.updateSurvey(surveyVo2);
    }
    @PutMapping("{id}")
    public Result updateSurveyStatus(@PathVariable Integer id, Integer status) {
        return surveyService.updateSurveyStatus(id, status);
    }

    @DeleteMapping("{id}")
    public Result deleteSurvey(@PathVariable Integer id) {
        return surveyService.deleteSurvey(id);
    }

    @GetMapping
    public Result listSurvey(Integer pageNum, Integer pageSize, Integer status) {
        return surveyService.listSurvey(pageNum, pageSize, status);
    }

    @GetMapping("name")
    public Result listSurveyName() {
        return surveyService.listSurveyName();
    }

    //TODO 分析问卷的答题情况

}
