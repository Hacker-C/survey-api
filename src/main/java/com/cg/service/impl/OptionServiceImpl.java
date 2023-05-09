package com.cg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cg.mapper.OptionMapper;
import com.cg.mapper.QuestionMapper;
import com.cg.pojo.*;
import com.cg.pojo.dto.OptionDto;
import com.cg.pojo.dto.PageDto;
import com.cg.pojo.vo.OptionVo;
import com.cg.pojo.vo.OptionVo2;
import com.cg.result.Result;
import com.cg.service.OptionService;
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

import static com.cg.util.AssistUtil.assertionWithRuntimeException;
import static com.cg.util.AssistUtil.assertionWithSystemException;
import static com.cg.util.SystemConst.*;

/**
* @author WeepLee
* @description 针对表【t_option】的数据库操作Service实现
* @createDate 2023-04-15 22:31:54
*/
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option>
    implements OptionService{


    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result saveOption(OptionVo optionVo) {
        Integer questionId = optionVo.getQuestionId();Integer sort = optionVo.getSort();
        String content = optionVo.getContent();
        assertionWithSystemException(!StringUtils.hasText(content), CONTENT_NOT_EMPTY);
        boolean success = Objects.isNull(sort) || sort < 0;
        assertionWithSystemException(success, SORT_ERROR);
        assertionWithSystemException(Objects.isNull(questionId), QUESTION_NOT_EXIST);
        long count = questionMapper.countQuestions(getUserId(), questionId);
        assertionWithSystemException(count < 1, QUESTION_NOT_EXIST);

        assertionWithSystemException(count(new LambdaQueryWrapper<Option>()
                .eq(Option::getContent, content)
                .eq(Option::getQuestionId, questionId)) > 0, OPTION_EXIST);
        Option option = CopyBeanUtil.copy(optionVo, Option.class);
        return save(option) ? Result.ok() : Result.fail(SAVE_FAIL);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updateOption(OptionVo2 optionVo2) {
        Integer questionId = optionVo2.getQuestionId();Integer sort = optionVo2.getSort();
        String content = optionVo2.getContent(); Integer id = optionVo2.getId();
        assertionWithSystemException(!StringUtils.hasText(content), CONTENT_NOT_EMPTY);
        boolean success = Objects.isNull(sort) || sort < 0;
        assertionWithSystemException(success, SORT_ERROR);
        assertionWithSystemException(Objects.isNull(questionId), QUESTION_NOT_EXIST);
        long count = questionMapper.countQuestions(getUserId(), questionId);
        assertionWithSystemException(count < 1, QUESTION_NOT_EXIST);

        assertionWithSystemException(count(new LambdaQueryWrapper<Option>()
                .eq(Option::getContent, content)
                .eq(Option::getQuestionId, questionId)) > 0, OPTION_EXIST);
        success = Objects.isNull(id) || Objects.isNull(getById(id));
        assertionWithSystemException(success, OPTION_NOT_EXIST);
        Option option = CopyBeanUtil.copy(optionVo2, Option.class);
        return updateById(option) ? Result.ok() : Result.fail(UPDATE_FAIL);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result deleteOption(Integer id) {
        Option option = getById(id);
        assertionWithSystemException(Objects.isNull(option), OPTION_NOT_EXIST);
        long count = questionMapper.countQuestions(getUserId(), option.getQuestionId());
        assertionWithSystemException(count < 1, QUESTION_NOT_EXIST);
        return removeById(option) ? Result.ok() : Result.fail(DELETE_FAIL);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result listOption(Integer pageNum, Integer pageSize, Integer questionId) {
        assertionWithSystemException(Objects.isNull(pageNum) || Objects.isNull(pageSize), PARAMETER_ERROR);
        boolean success = Objects.nonNull(questionId) && Objects.isNull(questionService.getById(questionId));
        assertionWithRuntimeException(success, SURVEY_NOT_EXIST);
        Page<Option> pageInfo  = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Option> queryWrapper;
        //根据不同的角色展示对应的数据
        User user = userService.getById(getUserId());
        if(user.getRole() == 1)
            queryWrapper = new LambdaQueryWrapper<Option>().eq(Objects.nonNull(questionId), Option::getQuestionId, questionId);
        else {
            queryWrapper = new LambdaQueryWrapper<>();
            if(Objects.nonNull(questionId))
                queryWrapper.eq(Option::getQuestionId, questionId);
            else {
                //查询该用户下的问卷
                List<Integer> surveyIds = surveyService.list(new LambdaQueryWrapper<Survey>().
                                eq(Survey::getUserId, user.getId()))
                        .stream().map(Survey::getId).collect(Collectors.toList());
                List<Integer> questionIds = questionService.list(new LambdaQueryWrapper<Question>()
                                .in(Question::getSurveyId, surveyIds).orderByAsc(Question::getSort))
                        .stream().map(Question::getId).collect(Collectors.toList());
                queryWrapper.in(Option::getQuestionId, questionIds);
            }
        }
        page(pageInfo, queryWrapper);
        PageDto<OptionDto> pageDto = CopyBeanUtil.copyPage(pageInfo.getTotal(), pageInfo.getRecords(), OptionDto.class);
        return Result.ok(pageDto);
    }

    private Long getUserId() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUser().getId();
    }

}




