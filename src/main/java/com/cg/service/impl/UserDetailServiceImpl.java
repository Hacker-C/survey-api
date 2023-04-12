package com.cg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cg.pojo.LoginUser;
import com.cg.pojo.User;
import com.cg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.cg.util.AssistUtil.assertionWithRuntimeException;
import static com.cg.util.SystemConst.*;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        assertionWithRuntimeException(Objects.isNull(user), USER_NOT_EXIST);
        assertionWithRuntimeException(FLAG_1.equals(user.getStatus()), USER_FORBIDDEN);
        return new LoginUser(user);
    }
}
