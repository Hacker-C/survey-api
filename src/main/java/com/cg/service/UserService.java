package com.cg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cg.pojo.User;
import com.cg.pojo.dto.UserDto;
import com.cg.pojo.vo.LoginVo;
import com.cg.pojo.vo.PasswordVo;
import com.cg.result.Result;

/**
* @author WeepLee
* @description 针对表【user】的数据库操作Service
* @createDate 2023-04-15 22:31:17
*/
public interface UserService extends IService<User> {

    Result saveUser(LoginVo loginVo);

    Result login(String username, String password);
    Result logout();

    Result getUser();

    Result updateUser(UserDto userDto);

    Result listUser(Integer pageNum, Integer pageSize, String nickname);

    Result forbidUser(Long id, Integer status);


    Result deleteUser(Long id);

    Result updatePassword(PasswordVo passwordVo);
}
