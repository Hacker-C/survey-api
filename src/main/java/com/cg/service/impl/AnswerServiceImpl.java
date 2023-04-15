package com.cg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cg.mapper.AnswerMapper;
import com.cg.mapper.QuestionMapper;
import com.cg.pojo.*;
import com.cg.pojo.dto.*;
import com.cg.result.Result;
import com.cg.service.*;
import com.cg.util.CopyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cg.util.AssistUtil.assertionWithSystemException;
import static com.cg.util.SystemConst.SURVEY_NOT_EXIST;
import static com.cg.util.SystemConst.SURVEY_NOT_PUBLISH;

/**
* @author WeepLee
* @description 针对表【answer】的数据库操作Service实现
* @createDate 2023-04-15 22:31:33
*/
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer>
    implements AnswerService{

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private OptionService optionService;

    @Override
    public Result analysisAnswer(Integer surveyId) {
//        Survey survey = surveyService.getById(surveyId);
//        boolean success = Objects.isNull(survey) || !survey.getUserId().equals(getUserId());
//        assertionWithSystemException(success, SURVEY_NOT_EXIST);
//        assertionWithSystemException(survey.getStatus() != 1, SURVEY_NOT_PUBLISH);
//        AnswerDto answerDto = CopyBeanUtil.copy(survey, AnswerDto.class);
//        List<Question> questions = questionService.list(new LambdaQueryWrapper<Question>().eq(Question::getSurveyId, surveyId));
//        questions.stream().map(question -> {
//            QuestionDto4 questionDto4 = CopyBeanUtil.copy(question, QuestionDto4.class);
//            long total = optionService.count(new LambdaQueryWrapper<Option>().eq(Option::getQuestionId, question.getId()));
//            List<Option> options = optionService.list(new LambdaQueryWrapper<Option>().eq(Option::getQuestionId, question.getId()));
//            options.stream().map(option -> {
//                OptionDto2 optionDto2 = CopyBeanUtil.copy(option, OptionDto2.class);
//                getOne(new LambdaQueryWrapper<Answer>().eq(Answer::getSurveyId, surveyId)
//                        .eq(Answer::getQuestionId, question.getId()));
//            })
//        }

        return null;


    }

    private Long getUserId() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUser().getId();
    }
}




