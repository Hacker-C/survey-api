package com.cg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cg.pojo.LinkSurvey;
import com.cg.result.Result;

/**
* @author WeepLee
* @description 针对表【link_survey】的数据库操作Service
* @createDate 2023-04-15 22:31:30
*/
public interface LinkSurveyService extends IService<LinkSurvey> {

    Result getSurveyByLinkId(Integer id);

    Result<String> getLinkBySurveyId(Integer surveyId);
}
