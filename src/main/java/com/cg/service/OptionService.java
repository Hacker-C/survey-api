package com.cg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cg.pojo.Option;
import com.cg.pojo.vo.OptionVo;
import com.cg.pojo.vo.OptionVo2;
import com.cg.result.Result;

/**
* @author WeepLee
* @description 针对表【t_option】的数据库操作Service
* @createDate 2023-04-15 22:31:54
*/
public interface OptionService extends IService<Option> {

    Result saveOption(OptionVo optionVo);

    Result updateOption(OptionVo2 optionVo2);

    Result deleteOption(Integer id);

    Result listOption(Integer pageNum, Integer pageSize, Integer questionId);
}
