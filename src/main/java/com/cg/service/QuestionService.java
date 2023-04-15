package com.cg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cg.pojo.Question;
import com.cg.pojo.vo.QuestionVo;
import com.cg.pojo.vo.QuestionVo2;
import com.cg.result.Result;

/**
* @author WeepLee
* @description 针对表【question】的数据库操作Service
* @createDate 2023-04-15 22:31:26
*/
public interface QuestionService extends IService<Question> {

    Result saveQuestion(QuestionVo questionVo);

    Result updateQuestion(QuestionVo2 questionVo2);

    Result deleteQuestion(Integer id);

    Result listQuestion(Integer pageNum, Integer pageSize, Integer surveyId);

    Result listQuestionName(Integer surveyId);
}
