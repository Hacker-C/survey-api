package com.cg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cg.mapper.AnswerMapper;
import com.cg.pojo.*;
import com.cg.pojo.dto.AnswerDto;
import com.cg.pojo.dto.OptionDto2;
import com.cg.pojo.dto.QuestionDto4;
import com.cg.pojo.vo.AnswerVo;
import com.cg.pojo.vo.AnswerVo2;
import com.cg.result.Result;
import com.cg.service.AnswerService;
import com.cg.service.LogService;
import com.cg.service.QuestionService;
import com.cg.service.SurveyService;
import com.cg.util.CopyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cg.util.AssistUtil.assertionWithSystemException;
import static com.cg.util.SystemConst.*;

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
    private SurveyService surveyService;

    @Autowired
    private LogService logService;



    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result analysisAnswer(Integer surveyId) {
        Survey survey = surveyService.getById(surveyId);
        boolean success = Objects.isNull(survey) || !survey.getUserId().equals(getUserId());
        assertionWithSystemException(success, SURVEY_NOT_EXIST);
        assertionWithSystemException(survey.getStatus() != 1, SURVEY_NOT_PUBLISH);
        AnswerDto answerDto = CopyBeanUtil.copy(survey, AnswerDto.class);
        Long count = logService.count(new LambdaQueryWrapper<Log>().eq(Log::getSurveyId, surveyId));
        answerDto.setTotal(count);
        List<Question> questions = questionService.list(new LambdaQueryWrapper<Question>().eq(Question::getSurveyId, surveyId)
                .orderByAsc(Question::getSort));
        List<QuestionDto4> questionDto4s = questions.stream().map(question -> {
            QuestionDto4 questionDto4 = CopyBeanUtil.copy(question, QuestionDto4.class);
            // 查询全部的答案
            List<Answer> answers = list(new LambdaQueryWrapper<Answer>().eq(Answer::getSurveyId, surveyId).
                    eq(Answer::getQuestionId, question.getId()));
            int total = answers.size();
            Map<String, List<Answer>> map = answers.stream().filter(answer -> !answer.getOptionId().equals(0))
                    .collect(Collectors.groupingBy(Answer::getContent));
            int sum = 0;
            List<OptionDto2> list = new ArrayList<>();
            for (Map.Entry<String, List<Answer>> entry : map.entrySet()) {
                int size = entry.getValue().size();
                String content = entry.getKey();
                OptionDto2 optionDto2 = new OptionDto2();
                int percent = new Double(Math.floor(1.0 * size / total * 100)).intValue();
                sum += percent;
                optionDto2.setContent(content).setNumber(size).setPercent(percent);
                list.add(optionDto2);
            }



            if (list.size() > 1 && sum != 100) {
                int percent = list.get(0).getPercent();
                list.get(0).setPercent(percent + 100 - sum);
            }
            List<Answer> answerList = answers.stream().filter(answer -> answer.getOptionId().equals(0)).collect(Collectors.toList());
            for (Answer answer : answerList)
                list.add(CopyBeanUtil.copy(answer, OptionDto2.class));
            questionDto4.setOptions(list);
            return questionDto4;
        }).collect(Collectors.toList());
        answerDto.setQuestions(questionDto4s);
        answerDto.setNickname(getUser().getNickname());
        return Result.ok(answerDto);


    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result saveAnswer(AnswerVo answerVo) {
        Integer surveyId = answerVo.getSurveyId();
        assertionWithSystemException(Objects.isNull(surveyId), SURVEY_NOT_EXIST);
        Survey survey = surveyService.getById(surveyId);
        assertionWithSystemException(Objects.isNull(survey), SURVEY_NOT_EXIST);
        assertionWithSystemException(survey.getStatus() != 1, SURVEY_NOT_PUBLISH);
        List<AnswerVo2> answerVo2s = answerVo.getAnswers();
        assertionWithSystemException(Objects.isNull(answerVo2s) || answerVo2s.size() == 0, ANSWER_NOT_EMPTY);
        List<Answer> answers = answerVo2s.stream().map(item -> {
            assertionWithSystemException(!StringUtils.hasText(item.getContent()), CONTENT_NOT_EMPTY);
            boolean success = Objects.isNull(item.getQuestionId()) || Objects.isNull(questionService.getById(item.getQuestionId()));
            assertionWithSystemException(success, QUESTION_NOT_EXIST);
            Answer answer = CopyBeanUtil.copy(item, Answer.class);
            answer.setSurveyId(surveyId);
            return answer;
        }).collect(Collectors.toList());
        // 执行完毕后记录用户的IP地址
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String IP = requestAttributes.getRequest().getRemoteHost();
        Log log = new Log();
        log.setIp(IP);
        log.setSurveyId(surveyId);
        logService.save(log);
        return saveBatch(answers) ? Result.ok() : Result.fail(SAVE_FAIL);
    }

    private Long getUserId() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUser().getId();
    }

    private User getUser() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUser();
    }
}




