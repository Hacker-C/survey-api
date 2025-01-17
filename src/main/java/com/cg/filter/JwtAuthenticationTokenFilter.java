package com.cg.filter;

import com.alibaba.fastjson.JSON;
import com.cg.pojo.LoginUser;
import com.cg.result.Result;
import com.cg.util.JWTUtil;
import com.cg.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.cg.util.SystemConst.LOGIN_KEY;
import static com.cg.util.SystemConst.NO_LOGIN;
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("authorization");
        if(!StringUtils.hasText(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String useId = null;
        try {
            useId = JWTUtil.getUserIdFromToken(token);
        } catch (Exception e) {
            Result result = Result.fail(NO_LOGIN);
            WebUtil.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }

        LoginUser user = (LoginUser)redisTemplate.opsForValue().get(LOGIN_KEY + useId);
        if(Objects.isNull(user)) {
            Result result = Result.fail(NO_LOGIN);
            WebUtil.renderString(httpServletResponse,JSON.toJSONString(result));
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
