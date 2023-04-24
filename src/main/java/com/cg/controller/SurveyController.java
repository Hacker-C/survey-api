package com.cg.controller;

import com.cg.pojo.dto.PageDto;
import com.cg.pojo.dto.QuestionDto3;
import com.cg.pojo.dto.SurveyDto;
import com.cg.pojo.dto.SurveyDto2;
import com.cg.pojo.vo.SurveyVo;
import com.cg.pojo.vo.SurveyVo2;
import com.cg.result.Result;
import com.cg.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/like/{id}")
    public Result updateSurveyLike(@PathVariable Integer id, Integer isLike) {
        return surveyService.updateSurveyLike(id, isLike);
    }

    @DeleteMapping("{id}")
    public Result deleteSurvey(@PathVariable Integer id) {
        return surveyService.deleteSurvey(id);
    }

    @GetMapping
    public Result<PageDto<SurveyDto>> listSurvey(Integer pageNum, Integer pageSize, Integer status, String surveyName) {
        return surveyService.listSurvey(pageNum, pageSize, status, surveyName);
    }

    @GetMapping("recycle")
    public Result<PageDto<SurveyDto2>> listRecycleSurvey(Integer pageNum, Integer pageSize, String surveyName) {
        return surveyService.listRecycleSurvey(pageNum, pageSize, surveyName);
    }

    @GetMapping("like")
    public Result<PageDto<SurveyDto2>> listLikeSurvey(Integer pageNum, Integer pageSize, String surveyName) {
        return surveyService.listLikeSurvey(pageNum, pageSize, surveyName);
    }
    /**
     * 辅助搜索问卷下的问题
     * @return
     */

    @GetMapping("name")
    public Result<List<SurveyDto2>> listSurveyName() {
        return surveyService.listSurveyName();
    }

    /**
     * 根据问卷ID获取整个问卷的信息
     * @param id
     * @return
     */

    @GetMapping("{id}")
    public Result<List<QuestionDto3>> getSurveyOverAll(@PathVariable Integer id) {
        return surveyService.getSurveyOverAll(id);
    }


}
