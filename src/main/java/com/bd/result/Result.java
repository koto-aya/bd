package com.bd.result;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 夕雾
 * 统一结果返回类
 */
@Data
@Slf4j
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    private Result(){}


    public static <T> Result<T> ok(){
        return build(ResultEnum.SUCCESS, null);
    }

    public static <T> Result<T> ok(T data){
        return build(ResultEnum.SUCCESS, data);
    }

    public static <T> Result<T> fail(){
        return build(ResultEnum.FAIL, null);
    }

    public static <T> Result<T> fail(String message){
        Result<T> result = build();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(ResultEnum resultEnum){
        log.error(resultEnum.getMessage());
        return build(resultEnum, null);
    }

    public static <T> Result<T> error(Integer code,String message){
        log.error(message);
        return build(code,message);
    }

    protected static <T> Result<T> build(){
        Result<T> result = new Result<>();
        return result;
    }

    protected static <T> Result<T> build(Integer code,String message){
        Result<T> result = build();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    protected static <T> Result<T> build(ResultEnum resultEnum,T data){
        Result<T> result = build(resultEnum.getCode(),resultEnum.getMessage());
        result.setData(data);
        return result;
    }
}
