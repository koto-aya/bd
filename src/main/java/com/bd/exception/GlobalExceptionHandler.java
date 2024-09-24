package com.bd.exception;

import com.bd.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author 夕雾
 */
@RestControllerAdvice()
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        log.error("runtime error "+ e);
        return Result.fail(e.getMessage());
    }
    @ExceptionHandler(XiwuReadException.class)
    public Result serviceError(XiwuReadException e){
        log.error(e.getMessage());
        return Result.error(e.getCode(),e.getMessage());
    }
}
