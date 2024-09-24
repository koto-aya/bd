package com.bd.controller;

import com.bd.model.request.LoginRequest;
import com.bd.model.request.RegisterRequest;
import com.bd.result.Result;
import com.bd.result.ResultEnum;
import com.bd.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/23 23:07
 */
@RestController
@RequestMapping("/auth/user/")
public class UserController {
    @Autowired
    private UsersService usersService;
    /**
     * @description: 用户注册
     * @param request: 注册信息
     * @return com.bd.result.Result
     * @author 夕雾
     * @date 2024/9/24 17:25
     */
    @PostMapping("/register")
    public Result<Object> register(@RequestBody RegisterRequest request){
        String userAccount = request.getUserAccount();
        String userPassword = request.getUserPassword();
        String checkPassword = request.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword))
            return Result.error(ResultEnum.INCORRECT_ACCOUNT_OR_PASSWORD);
        boolean success=usersService.register(request);
        return success?Result.ok():Result.fail();
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest request){
        //todo 从redis中获取token
        //用户账户可能是手机号或邮箱号
        String userAccount = request.getUserAccount();
        String userPassword = request.getUserPassword();
        String checkCode=request.getCheckCode();
        String loginMethod = request.getLoginMethod();
        //判断登录方式，调用合适接口
        String token="";
        if ("mobile".equalsIgnoreCase(loginMethod)){
            token=usersService.loginByMobile(userAccount,checkCode);
        }else if ("userAccount".equalsIgnoreCase(loginMethod)){
            token=usersService.loginByUserAccount(userAccount,userPassword);
        }else if ("email".equalsIgnoreCase(loginMethod)){
            token=usersService.loginByEmail(userAccount,userPassword);
        }else{
            return Result.error(ResultEnum.UNSUPPORTD_LOGIN_METHOD);
        }
        return Result.ok(token);
    }
}
