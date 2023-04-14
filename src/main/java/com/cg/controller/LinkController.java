package com.cg.controller;

import com.cg.result.Result;
import com.cg.service.LinkSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result getSurveyByLinkId(@PathVariable Integer id) {
        return linkSurveyService.getSurveyByLinkId(id);
    }
}
