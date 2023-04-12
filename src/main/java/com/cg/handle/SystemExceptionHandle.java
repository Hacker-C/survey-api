package com.cg.handle;

import com.cg.result.Result;
import com.cg.result.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import static com.cg.util.SystemConst.*;
@Slf4j
@RestControllerAdvice
public class SystemExceptionHandle {
    @ExceptionHandler(RuntimeException.class)
    public Result RuntimeExceptionHandle(RuntimeException exception) throws Exception {
        log.error("出现了运行时异常：", exception );
        if(exception instanceof AccessDeniedException) {
            throw exception;
        }else if(exception instanceof HttpMessageNotReadableException) {
            return Result.fail(PARAMETER_ERROR);
        }else if(exception instanceof MethodArgumentTypeMismatchException) {
            return Result.fail(INTEGER_TRANSFORM_ERROR);
        }else if(exception instanceof BadCredentialsException) {
            throw exception;
        }
        return Result.fail(exception.getMessage());
    }
    @ExceptionHandler(SystemException.class)
    public Result GlobalExceptionHandle(SystemException globalException) {
        log.error("出现了全局异常: ", globalException );
        return Result.fail(globalException.getMsg());
    }
}
