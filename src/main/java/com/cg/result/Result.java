package com.cg.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

import static com.cg.util.SystemConst.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
    private Integer code;

    private String msg;

    private T data;

    public static <T> Result<T> ok() {
        Result r = new Result();
        r.code = OK;
        r.msg = SUCCESS;
        return r;
    }

    public static <T> Result<T> ok(T data) {
        Result r = new Result();
        r.code = OK;
        r.msg = SUCCESS;
        r.data = data;
        return r;
    }

    public static <T> Result<T> fail(String msg) {
        Result r = new Result();
        r.code = ERROR;
        r.msg = msg;
        return r;
    }
}
