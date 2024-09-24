package com.bd.model.request;

import lombok.Data;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/24 18:04
 */
@Data
public class LoginRequest {
    private String userAccount;//用户登录的账户可能是手机号或邮箱号
    private String userPassword;
    private String checkCode;//验证码(手机登录时使用)
    private String loginMethod;//登录方式
}
