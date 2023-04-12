package com.cg.handle;

import com.alibaba.fastjson.JSON;
import com.cg.result.Result;
import com.cg.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cg.util.SystemConst.NO_AUTHORITY;

@Component
@Slf4j
public class AccessDeniedHandleImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.info("AccessDeniedHandler get");
        WebUtil.renderString(httpServletResponse, JSON.toJSONString(Result.fail(NO_AUTHORITY)));
    }
}
