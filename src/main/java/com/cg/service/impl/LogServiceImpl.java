package com.cg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cg.pojo.Log;
import com.cg.service.LogService;
import com.cg.mapper.LogMapper;
import org.springframework.stereotype.Service;

/**
* @author WeepLee
* @description 针对表【t_log】的数据库操作Service实现
* @createDate 2023-04-16 16:10:37
*/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
    implements LogService{

}




