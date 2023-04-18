package com.cg.controller;

import com.cg.pojo.dto.PageDto;
import com.cg.pojo.dto.QuestionDto;
import com.cg.pojo.dto.QuestionDto2;
import com.cg.pojo.vo.QuestionVo;
import com.cg.pojo.vo.QuestionVo2;
import com.cg.result.Result;
import com.cg.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @PostMapping
    public Result saveQuestion(@RequestBody QuestionVo questionVo) {
        return questionService.saveQuestion(questionVo);
    }

    @PutMapping
    public Result updateQuestion(@RequestBody QuestionVo2 questionVo2) {
        return questionService.updateQuestion(questionVo2);
    }

    @DeleteMapping("{id}")
    public Result deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }

    @GetMapping
    public Result<PageDto<QuestionDto>> listQuestion(Integer pageNum, Integer pageSize, Integer surveyId) {
        return questionService.listQuestion(pageNum, pageSize, surveyId);
    }

    /**
     * 辅助搜索问题下的选项
     * @param surveyId
     * @return
     */
    @GetMapping("name")
    public Result<List<QuestionDto2>> listQuestionName(Integer surveyId) {
        return questionService.listQuestionName(surveyId);
    }
}
