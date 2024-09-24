package com.bd.result;

import lombok.Getter;

/**
 * @author 夕雾
 * 返回结果枚举类
 */
@Getter
public enum ResultEnum {
    SUCCESS(200,"成功"),
    FAIL(201,"失败"),
    USER_NOT_LOGIN(301,"用户未登录"),
    UNSUPPORTD_LOGIN_METHOD(302,"不支持的登录方式"),
    USER_NOT_EXIST(305,"用户未注册"),
    USER_ALREADY_EXIST(306,"用户已存在"),
    SIMPLE_PASSWORD(307,"密码复杂度不符合要求"),
    PASSWORD_INCONSISTENCY(308,"密码和确认密码不一致"),
    ACCOUNT_TOO_SHORT(309,"用户账号过短或过长"),
    INCORRECT_ACCOUNT_OR_PASSWORD(310,"账户或密码错误"),
    CANNOT_CONTAIN_SPECIAL_CHAR(311,"不能包含特殊字符"),
    ILLEGAL_CELL_PHONE_NUM(312,"不是一个合法的手机号"),
    ILLEGAL_PARAMETER(401,"非法参数"),
    FILE_UPLOAD_FAIL(402,"文件上传失败"),
    CATEGORY_NOT_FOUND(404,"该分类不存在"),
    INACCESSIBLE_IMAGE_LINK(405,"不能访问的图片链接"),
    BANK_NOT_FOUND(406,"题库不存在"),
    CHOICE_QUESTION_OPTION_TOO_FEW(407,"提供的选项过少"),
    QUESTION_NOT_FOUND(408,"题目不存在");


    private Integer code;
    private String message;

    ResultEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
