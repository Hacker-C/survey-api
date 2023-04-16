package com.cg.handle;

import com.alibaba.fastjson.JSON;
import com.cg.result.Result;
import com.cg.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cg.util.SystemConst.*;

@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.error("authentication entry point:{}", e.getMessage());
        log.error("request path:{}", httpServletRequest.getRequestURL());
        String result = null;
        if(e instanceof BadCredentialsException) result = JSON.toJSONString(Result.fail(USERNAME_OR_PASSWORD_ERROR));
        else if(e instanceof InsufficientAuthenticationException)result = JSON.toJSONString(Result.fail(NO_AUTHORITY));
        else result = JSON.toJSONString(Result.fail( AUTHORITY_ERROR));
        WebUtil.renderString(httpServletResponse, result);
    }
}
