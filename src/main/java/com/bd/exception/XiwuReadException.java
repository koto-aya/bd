package com.bd.exception;

import com.bd.result.ResultEnum;
import lombok.Getter;

/**
 * 自定义异常类
 * @author 夕雾
 */
@Getter
public class XiwuReadException extends RuntimeException{
    private Integer code;
    private String message;
    public XiwuReadException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code= resultEnum.getCode();
        this.message= resultEnum.getMessage();
    }
}
