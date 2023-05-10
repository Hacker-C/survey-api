package com.cg.controller;

import com.cg.pojo.dto.SurveyDto3;
import com.cg.result.Result;
import com.cg.service.LinkSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("link")
public class LinkController {

    @Autowired
    private LinkSurveyService linkSurveyService;

    /**
     * 根据链接ID 返回完整的问卷内容
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result<SurveyDto3> getSurveyByLinkId(@PathVariable String name) {
        return linkSurveyService.getSurveyByLinkId(name);
    }

    @GetMapping("name")
    public Result<String> getLinkBySurveyId(Integer surveyId) {
        return linkSurveyService.getLinkBySurveyId(surveyId);
    }
}
