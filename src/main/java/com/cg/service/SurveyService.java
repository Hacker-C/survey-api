package com.cg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cg.pojo.Survey;
import com.cg.pojo.vo.SurveyVo;
import com.cg.pojo.vo.SurveyVo2;
import com.cg.result.Result;

/**
* @author WeepLee
* @description 针对表【survey】的数据库操作Service
* @createDate 2023-04-15 22:31:23
*/
public interface SurveyService extends IService<Survey> {

    Result saveSurvey(SurveyVo surveyVo);

    Result updateSurvey(SurveyVo2 surveyVo2);

    Result updateSurveyStatus(Integer id, Integer status);

    Result deleteSurvey(Integer id);

    Result listSurvey(Integer pageNum, Integer pageSize, Integer status);

    Result listSurveyName();

    Result getSurveyOverAll(Integer id);
}
