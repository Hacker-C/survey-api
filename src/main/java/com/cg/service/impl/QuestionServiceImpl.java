package com.cg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cg.mapper.QuestionMapper;
import com.cg.pojo.LoginUser;
import com.cg.pojo.Question;
import com.cg.pojo.Survey;
import com.cg.pojo.User;
import com.cg.pojo.dto.PageDto;
import com.cg.pojo.dto.QuestionDto;
import com.cg.pojo.dto.QuestionDto2;
import com.cg.pojo.vo.QuestionVo;
import com.cg.pojo.vo.QuestionVo2;
import com.cg.result.Result;
import com.cg.service.QuestionService;
import com.cg.service.SurveyService;
import com.cg.service.UserService;
import com.cg.util.CopyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cg.util.AssistUtil.*;
import static com.cg.util.SystemConst.*;

/**
* @author WeepLee
* @description 针对表【question】的数据库操作Service实现
* @createDate 2023-04-15 22:31:26
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{
    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserService userService;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result saveQuestion(QuestionVo questionVo) {
        String title = questionVo.getTitle();Integer surveyId = questionVo.getSurveyId();
        Integer type = questionVo.getType(), required = questionVo.getRequired();
        assertionWithSystemException(!StringUtils.hasText(title), TITLE_NOT_EMPTY);
        Survey survey = surveyService.getById(surveyId);
        boolean success = Objects.isNull(survey) || !survey.getUserId().equals(getUserId());
        assertionWithSystemException(success, SURVEY_NOT_EXIST);
        assertionWithSystemException(survey.getStatus() != 0, SURVEY_PUBLISH);
        assertionWithSystemException(checkType(type), TYPE_ERROR);
        success = Objects.nonNull(required) && required != 1 && required != 0;
        assertionWithSystemException(success, REQUIRED_ERROR);
        assertionWithSystemException(count(new LambdaQueryWrapper<Question>().eq(Question::getTitle, title)) > 0, TITLE_EXIST);
        Question question = CopyBeanUtil.copy(questionVo, Question.class);
        return save(question) ? Result.ok() : Result.fail(SAVE_FAIL);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updateQuestion(QuestionVo2 questionVo2) {
        String title = questionVo2.getTitle();Integer surveyId = questionVo2.getSurveyId();
        Integer type = questionVo2.getType(), required = questionVo2.getRequired();
        Integer id = questionVo2.getId();
        assertionWithSystemException(!StringUtils.hasText(title), TITLE_NOT_EMPTY);
        Survey survey = surveyService.getById(surveyId);
        boolean success = Objects.isNull(survey) || !survey.getUserId().equals(getUserId());
        assertionWithSystemException(success, SURVEY_NOT_EXIST);
        assertionWithSystemException(survey.getStatus() != 0, SURVEY_PUBLISH);
        assertionWithSystemException(checkType(type), TYPE_ERROR);
        success = Objects.nonNull(required) && (required != 1 && required != 0);
        assertionWithSystemException(success, REQUIRED_ERROR);
        assertionWithSystemException(count(new LambdaQueryWrapper<Question>().eq(Question::getTitle, title)) > 0, TITLE_EXIST);
        success = Objects.isNull(id) || Objects.isNull(getById(id));
        assertionWithSystemException(success, QUESTION_NOT_EXIST);
        Question question = CopyBeanUtil.copy(questionVo2, Question.class);
        return updateById(question) ? Result.ok() : Result.fail(UPDATE_FAIL);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result deleteQuestion(Integer id) {
        Question question = getById(id);
        assertionWithSystemException(Objects.isNull(question), QUESTION_NOT_EXIST);
        Survey survey = surveyService.getOne(new LambdaQueryWrapper<Survey>().eq(Survey::getId, question.getSurveyId()));
        boolean success = Objects.isNull(survey) || !survey.getUserId().equals(getUserId());
        assertionWithSystemException(success, SURVEY_NOT_EXIST);
        return removeById(question) ? Result.ok() : Result.fail(DELETE_FAIL);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result listQuestion(Integer pageNum, Integer pageSize, Integer surveyId) {
        assertionWithSystemException(Objects.isNull(pageNum) || Objects.isNull(pageSize), PARAMETER_ERROR);
        boolean success = Objects.nonNull(surveyId) && Objects.isNull(surveyService.getById(surveyId));
        assertionWithRuntimeException(success, SURVEY_NOT_EXIST);
        Page<Question> pageInfo  = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Question> queryWrapper;
        //根据不同的角色展示对应的数据
        User user = userService.getById(getUserId());
        if(user.getRole() == 1)
            queryWrapper = new LambdaQueryWrapper<Question>().eq(Objects.nonNull(surveyId), Question::getSurveyId, surveyId)
                    .orderByAsc(Question::getSort);
        else {

            queryWrapper = new LambdaQueryWrapper<>();
            if(Objects.nonNull(surveyId))
                queryWrapper.eq(Question::getSurveyId, surveyId);
            else {
                //查询该用户下的问卷
                List<Integer> surveyIds = surveyService.list(new LambdaQueryWrapper<Survey>().
                        eq(Survey::getUserId, user.getId()))
                        .stream().map(Survey::getId).collect(Collectors.toList());
                queryWrapper.in(Question::getSurveyId, surveyIds);
            }
            queryWrapper.orderByAsc(Question::getSort);
        }
        page(pageInfo, queryWrapper);
        PageDto<QuestionDto> pageDto = CopyBeanUtil.copyPage(pageInfo.getTotal(), pageInfo.getRecords(), QuestionDto.class);
        return Result.ok(pageDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result listQuestionName(Integer surveyId) {
        Survey survey = surveyService.getById(surveyId);
        assertionWithSystemException(Objects.isNull(survey), SURVEY_NOT_EXIST);
        List<Question> questions = list(new LambdaQueryWrapper<Question>().eq(Question::getSurveyId, surveyId));
        List<QuestionDto2> questionDto2s = CopyBeanUtil.copyList(questions, QuestionDto2.class);
        return Result.ok(questionDto2s);
    }

    private Long getUserId() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUser().getId();
    }


}




