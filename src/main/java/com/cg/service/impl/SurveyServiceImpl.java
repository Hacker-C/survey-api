package com.cg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cg.mapper.SurveyMapper;
import com.cg.pojo.*;
import com.cg.pojo.dto.*;
import com.cg.pojo.vo.SurveyVo;
import com.cg.pojo.vo.SurveyVo2;
import com.cg.result.Result;
import com.cg.service.*;
import com.cg.util.CopyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cg.util.AssistUtil.*;
import static com.cg.util.SystemConst.*;

/**
* @author WeepLee
* @description 针对表【survey】的数据库操作Service实现
* @createDate 2023-04-15 22:31:23
*/
@Service
public class SurveyServiceImpl extends ServiceImpl<SurveyMapper, Survey>
    implements SurveyService{

    @Autowired
    private LinkSurveyService linkSurveyService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private OptionService optionService;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result saveSurvey(SurveyVo surveyVo) {
        String title = surveyVo.getTitle();LocalDateTime expiration = surveyVo.getExpireTime();
        assertionWithSystemException(!StringUtils.hasText(title), TITLE_NOT_EMPTY);
        assertionWithSystemException(Objects.isNull(expiration), EXPIRE_TIME_NOT_EMPTY);
        assertionWithSystemException(checkTime(expiration), EXPIRE_TIME_ERROR);

        assertionWithSystemException(count(new LambdaQueryWrapper<Survey>().eq(Survey::getTitle, title)) > 0, TITLE_EXIST);
        Survey survey = CopyBeanUtil.copy(surveyVo, Survey.class);
        survey.setUserId(getUserId());

        return save(survey) ? Result.ok() : Result.fail(SAVE_FAIL);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updateSurvey(SurveyVo2 surveyVo2) {
        String title = surveyVo2.getTitle(); LocalDateTime expiration = surveyVo2.getExpireTime();
        Integer id = surveyVo2.getId(); Long userId = getUserId();
        assertionWithSystemException(!StringUtils.hasText(title), TITLE_NOT_EMPTY);
        assertionWithSystemException(Objects.isNull(expiration), EXPIRE_TIME_NOT_EMPTY);
        assertionWithSystemException(checkTime(expiration), EXPIRE_TIME_ERROR);
        Survey survey = getOne(new LambdaQueryWrapper<Survey>().eq(Survey::getTitle, title).eq(Survey::getUserId, userId));
        assertionWithSystemException(Objects.nonNull(survey), TITLE_EXIST);
        boolean success = Objects.isNull(id) || Objects.isNull(getById(id));
        assertionWithSystemException(success, SURVEY_NOT_EXIST);
        survey = CopyBeanUtil.copy(surveyVo2, Survey.class);
        survey.setUserId(userId);
        return updateById(survey) ? Result.ok() : Result.fail(UPDATE_FAIL);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updateSurveyStatus(Integer id, Integer status) {
        boolean success = Objects.isNull(status) || (status != 1 && status != 0 && status != 2);
        assertionWithRuntimeException(success, STATUS_ERROR);
        Survey survey = getById(id);
        success = Objects.isNull(survey) || !survey.getUserId().equals(getUserId());
        assertionWithSystemException(success, SURVEY_NOT_EXIST);
        LinkSurvey linkSurvey = linkSurveyService.getOne(new LambdaQueryWrapper<LinkSurvey>().eq(LinkSurvey::getSurveyId, id));
        boolean f1 = true, f2, f3;
        if(Objects.isNull(linkSurvey)) {
            linkSurvey = new LinkSurvey();
            linkSurvey.setSurveyId(id).setLink(getLink()).setStatus(status);
            f1 = linkSurveyService.save(linkSurvey);
        }
        linkSurvey.setStatus(status);
        survey.setStatus(status);
        f2 = linkSurveyService.updateById(linkSurvey);
        f3 = updateById(survey);
        success = f1 && f2 && f3;
        return success ? Result.ok() : Result.fail(UPDATE_FAIL);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result deleteSurvey(Integer id) {
        Survey survey = getById(id);
        boolean success = Objects.isNull(survey) || !survey.getUserId().equals(getUserId());
        assertionWithSystemException(success, SURVEY_NOT_EXIST);
        LambdaUpdateWrapper<Survey> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Survey::getId, id).set(Survey::getDeleted, FLAG_1);
        // 将链接也删除
        LinkSurvey linkSurvey = linkSurveyService.getOne(new LambdaQueryWrapper<LinkSurvey>().eq(LinkSurvey::getSurveyId, id));
        linkSurveyService.removeById(linkSurvey);
        return update(updateWrapper) ? Result.ok() : Result.fail(DELETE_FAIL);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result listSurvey(Integer pageNum, Integer pageSize, Integer status, String surveyName) {
        assertionWithSystemException(Objects.isNull(pageNum) || Objects.isNull(pageSize), PARAMETER_ERROR);
        boolean success = Objects.nonNull(status) && status != 1 && status != 0;
        assertionWithRuntimeException(success, STATUS_ERROR);
        Page<Survey> pageInfo  = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Survey> queryWrapper;
        //根据不同的角色展示对应的数据
        User user = userService.getById(getUserId());
        if(user.getRole() == 1)
            queryWrapper = new LambdaQueryWrapper<Survey>().eq(Objects.nonNull(status), Survey::getStatus, status)
                    .like(Objects.nonNull(surveyName), Survey::getTitle, surveyName)
                    .orderByAsc(Survey::getCreateTime);
        else {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Objects.nonNull(status), Survey::getStatus, status)
                    .like(Objects.nonNull(surveyName), Survey::getTitle, surveyName)
                    .eq(Survey::getUserId, user.getId()).orderByAsc(Survey::getCreateTime);
        }
        page(pageInfo, queryWrapper);
        PageDto<SurveyDto> pageDto = CopyBeanUtil.copyPage(pageInfo.getTotal(), pageInfo.getRecords(), SurveyDto.class);
        return Result.ok(pageDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result listSurveyName() {
        User user = userService.getById(getUserId());
        LambdaQueryWrapper<Survey> queryWrapper = null;
        if(user.getRole() == 0)
            queryWrapper = new LambdaQueryWrapper<Survey>().eq(Survey::getUserId, user.getId());
        List<SurveyDto2> surveys = CopyBeanUtil.copyList(list(queryWrapper), SurveyDto2.class);
        return Result.ok(surveys);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result getSurveyOverAll(Integer id) {
        Survey survey = getById(id);
        boolean success = Objects.isNull(survey) || !survey.getUserId().equals(getUserId());
        assertionWithSystemException(success, SURVEY_NOT_EXIST);
        assertionWithSystemException(survey.getStatus() != 1, SURVEY_NOT_PUBLISH);
        //填入用户信息
        SurveyDto3 surveyDto3 = CopyBeanUtil.copy(survey, SurveyDto3.class);
        List<Question> questions = questionService.list(new LambdaQueryWrapper<Question>().eq(Question::getSurveyId, id));
        List<QuestionDto3> questionDto3s = questions.stream().map(question -> {
            QuestionDto3 questionDto3 = CopyBeanUtil.copy(question, QuestionDto3.class);
            List<Option> options = optionService.list(new LambdaQueryWrapper<Option>().eq(Option::getQuestionId, question.getId()));
            questionDto3.setOptions(CopyBeanUtil.copyList(options, OptionDto.class));
            return questionDto3;
        }).collect(Collectors.toList());
        surveyDto3.setQuestions(questionDto3s);
        surveyDto3.setNickname(getUser().getNickname());
        return Result.ok(surveyDto3);
    }

    @Override
    public Result listRecycleSurvey(Integer pageNum, Integer pageSize, String surveyName) {
        assertionWithSystemException(Objects.isNull(pageNum) || Objects.isNull(pageSize), PARAMETER_ERROR);
        Page<Survey> pageInfo  = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Survey> queryWrapper;
        //根据不同的角色展示对应的数据
        User user = userService.getById(getUserId());
        if(user.getRole() == 1)
            queryWrapper = new LambdaQueryWrapper<Survey>().eq(Survey::getStatus, 2)
                    .like(Objects.nonNull(surveyName), Survey::getTitle, surveyName)
                    .orderByAsc(Survey::getCreateTime);
        else {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Survey::getStatus, 2)
                    .like(Objects.nonNull(surveyName), Survey::getTitle, surveyName)
                    .eq(Survey::getUserId, user.getId()).orderByAsc(Survey::getCreateTime);
        }
        page(pageInfo, queryWrapper);
        PageDto<SurveyDto> pageDto = CopyBeanUtil.copyPage(pageInfo.getTotal(), pageInfo.getRecords(), SurveyDto.class);
        return Result.ok(pageDto);
    }

    @Override
    public Result updateSurveyLike(Integer id, Integer isLike) {
        boolean success = Objects.isNull(isLike) || (isLike != 1 && isLike != 0);
        assertionWithRuntimeException(success, LIKE_ERROR);
        Survey survey = getById(id);
        success = Objects.isNull(survey) || !survey.getUserId().equals(getUserId());
        assertionWithSystemException(success, SURVEY_NOT_EXIST);
        survey.setIsLike(isLike);
        return updateById(survey) ? Result.ok() : Result.fail(UPDATE_FAIL);
    }

    @Override
    public Result listLikeSurvey(Integer pageNum, Integer pageSize, String surveyName) {
        assertionWithSystemException(Objects.isNull(pageNum) || Objects.isNull(pageSize), PARAMETER_ERROR);
        Page<Survey> pageInfo  = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Survey> queryWrapper;
        //根据不同的角色展示对应的数据
        User user = userService.getById(getUserId());
        if(user.getRole() == 1)
            queryWrapper = new LambdaQueryWrapper<Survey>().eq(Survey::getIsLike, 1)
                    .like(Objects.nonNull(surveyName), Survey::getTitle, surveyName)
                    .orderByAsc(Survey::getCreateTime);
        else {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Survey::getIsLike, 1)
                    .like(Objects.nonNull(surveyName), Survey::getTitle, surveyName)
                    .eq(Survey::getUserId, user.getId()).orderByAsc(Survey::getCreateTime);
        }
        page(pageInfo, queryWrapper);
        PageDto<SurveyDto> pageDto = CopyBeanUtil.copyPage(pageInfo.getTotal(), pageInfo.getRecords(), SurveyDto.class);
        return Result.ok(pageDto);
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




