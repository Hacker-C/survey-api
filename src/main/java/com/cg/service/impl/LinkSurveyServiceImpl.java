package com.cg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cg.mapper.LinkSurveyMapper;
import com.cg.pojo.LinkSurvey;
import com.cg.result.Result;
import com.cg.service.LinkSurveyService;
import com.cg.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.cg.util.AssistUtil.assertionWithSystemException;
import static com.cg.util.SystemConst.*;

/**
* @author WeepLee
* @description 针对表【link_survey】的数据库操作Service实现
* @createDate 2023-04-15 22:31:30
*/
@Service
public class LinkSurveyServiceImpl extends ServiceImpl<LinkSurveyMapper, LinkSurvey>
    implements LinkSurveyService{

    @Autowired
    private SurveyService surveyService;

    @Override
    public Result getSurveyByLinkId(Integer id) {
        LinkSurvey linkSurvey = getById(id);
        assertionWithSystemException(Objects.isNull(linkSurvey), LINK_NOT_EXIST);
        assertionWithSystemException(linkSurvey.getStatus() != 1, SURVEY_NOT_PUBLISH);
        Result result = surveyService.getSurveyOverAll(linkSurvey.getSurveyId());
        return Result.ok(result.getData());
    }
}




