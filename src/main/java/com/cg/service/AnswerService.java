package com.cg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cg.pojo.Answer;
import com.cg.result.Result;

/**
* @author WeepLee
* @description 针对表【answer】的数据库操作Service
* @createDate 2023-04-15 22:31:33
*/
public interface AnswerService extends IService<Answer> {

    Result analysisAnswer(Integer surveyId);
}
