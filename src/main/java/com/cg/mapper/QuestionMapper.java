package com.cg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author WeepLee
* @description 针对表【question】的数据库操作Mapper
* @createDate 2023-04-15 22:31:26
* @Entity com.cg.pojo.Question
*/
@Repository
public interface QuestionMapper extends BaseMapper<Question> {

    long countQuestions(@Param(value = "userId") Long userId,@Param(value = "questionId") Integer questionId);
}




