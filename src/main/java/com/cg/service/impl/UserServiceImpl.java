package com.cg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cg.mapper.UserMapper;
import com.cg.pojo.LoginUser;
import com.cg.pojo.User;
import com.cg.pojo.dto.LoginDto;
import com.cg.pojo.dto.PageDto;
import com.cg.pojo.dto.UserDto;
import com.cg.pojo.dto.UserDto2;
import com.cg.pojo.vo.LoginVo;
import com.cg.pojo.vo.PasswordVo;
import com.cg.result.Result;
import com.cg.service.UserService;
import com.cg.util.CopyBeanUtil;
import com.cg.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.cg.util.AssistUtil.*;
import static com.cg.util.SystemConst.*;

/**
* @author WeepLee
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-04-15 22:31:17
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result saveUser(LoginVo loginVo) {
        String username = loginVo.getUsername(), password = loginVo.getPassword(), repassword = loginVo.getRePassword();
        assertionWithSystemException(!StringUtils.hasText(username), USERNAME_NOT_EMPTY);
        assertionWithSystemException(!StringUtils.hasText(password), PASSWORD_NOT_EMPTY);
        assertionWithSystemException(!password.equals(repassword), PASSWORD_NOT_CONSISTENT);
        assertionWithSystemException(count(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) > 0, USER_EXIST);
        User user = CopyBeanUtil.copy(loginVo, User.class);
        user.setNickname(getNickname()).setPassword(passwordEncoder.encode(password));
        return save(user) ? Result.ok() : Result.fail(SAVE_FAIL);
    }

    @Override
    public Result login(String username, String password) {
        assertionWithRuntimeException(!StringUtils.hasText(username), USERNAME_NOT_EMPTY);
        assertionWithRuntimeException(!StringUtils.hasText(password), PASSWORD_NOT_EMPTY);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        assertionWithSystemException(Objects.isNull(authenticate), USERNAME_OR_PASSWORD_ERROR);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String token = JWTUtil.generateToken(id);
        redisTemplate.opsForValue().set(LOGIN_KEY + id, loginUser);
        User user = loginUser.getUser();
        LoginDto loginDto = CopyBeanUtil.copy(user, LoginDto.class);
        loginDto.setToken(token);
        return Result.ok(loginDto);
    }

    @Override
    public Result logout() {
        String key  = LOGIN_KEY + getUserId();
        return redisTemplate.delete(key) ? Result.ok() : Result.fail(EXIT_FAIL);
    }

    @Override
    public Result getUser() {
        User user = getById(getUserId());
        UserDto userDto = CopyBeanUtil.copy(user, UserDto.class);
        return Result.ok(userDto);
    }

    @Override
    public Result updateUser(UserDto userDto) {
        assertionWithRuntimeException(!Pattern.matches(EMAIL_REGEX, userDto.getEmail()), EMAIL_ERROR);
        assertionWithRuntimeException(!Pattern.matches(PHONE_REGEX, userDto.getPhone()), PHONE_ERROR);
        Integer gender = userDto.getGender();
        boolean success = Objects.isNull(gender) || (gender != 1 && gender != 0);
        assertionWithRuntimeException(success, GENDER_ERROR);
        User user = CopyBeanUtil.copy(userDto, User.class);
        user.setId(getUserId());
        return updateById(user) ? Result.ok() : Result.fail(UPDATE_FAIL);
    }

    @Override
    public Result listUser(Integer pageNum, Integer pageSize, String nickname) {
        assertionWithSystemException(Objects.isNull(pageNum) || Objects.isNull(pageSize), PARAMETER_ERROR);
        Page<User> pageInfo  = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(nickname), User::getNickname, nickname).orderByAsc(User::getCreateTime);
        page(pageInfo, queryWrapper);
        PageDto<UserDto2> pageDto = CopyBeanUtil.copyPage(pageInfo.getTotal(), pageInfo.getRecords(), UserDto2.class);
        return Result.ok(pageDto);
    }

    @Override
    public Result forbidUser(Long id, Integer status) {
        boolean success = Objects.isNull(status) || (status != 1 && status != 0);
        assertionWithRuntimeException(success, STATUS_ERROR);
        User user = getById(id);
        assertionWithSystemException(Objects.isNull(user), USER_NOT_EXIST);
        user.setStatus(status);
        return updateById(user) ? Result.ok() : Result.fail(FORBIDDEN_FAIL);
    }

    @Override
    public Result deleteUser(Long id) {
        User user = getById(id);
        assertionWithSystemException(Objects.isNull(user), USER_NOT_EXIST);
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, id).set(User::getDeleted, FLAG_1);
        return update(updateWrapper) ? Result.ok() : Result.fail(DELETE_FAIL);
    }

    @Override
    public Result updatePassword(PasswordVo passwordVo) {
        String password = passwordVo.getOldPassword(), newPassword = passwordVo.getNewPassword(), rePassword = passwordVo.getRePassword();
        assertionWithRuntimeException(!StringUtils.hasText(password), PASSWORD_NOT_EMPTY);
        assertionWithRuntimeException(!StringUtils.hasText(newPassword), NEW_PASSWORD_NOT_EMPTY);
        assertionWithRuntimeException(!newPassword.equals(rePassword), PASSWORD_NOT_CONSISTENT);
        User user = getById(getUserId());
        assertionWithSystemException(!passwordEncoder.matches(password, user.getPassword()), PASSWORD_ERROR);
        user.setPassword(passwordEncoder.encode(newPassword));
        return updateById(user) ? Result.ok() : Result.fail(UPDATE_FAIL);
    }


    private Long getUserId() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUser().getId();
    }
}




