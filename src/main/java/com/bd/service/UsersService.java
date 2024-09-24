package com.bd.service;

import com.bd.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bd.model.request.RegisterRequest;

/**
* @author kotoaya
* @description 针对表【users(用户表)】的数据库操作Service
* @createDate 2024-09-23 10:16:03
*/
public interface UsersService extends IService<User> {

    /**
     * @description: 用户注册
     * @param request: 注册信息
     * @return boolean
     * @author 夕雾
     * @date 2024/9/23 23:15
     */
    boolean register(RegisterRequest request);

    /**
     * @description: 通过手机号码和验证码登录
     * @param mobileNum: 手机号码
     * @param checkCode: 验证码
     * @return java.lang.String
     * @author 夕雾
     * @date 2024/9/24 19:32
     */
    String loginByMobile(String mobileNum,String checkCode);
    /**
     * @description: 通过账号和密码登录
     * @param userAccount: 账号
     * @param password: 密码
     * @return java.lang.String
     * @author 夕雾
     * @date 2024/9/24 19:32
     */
    String loginByUserAccount(String userAccount,String password);
    /**
     * @description: 通过邮箱登录
     * @param email: 邮箱
     * @param password: 密码
     * @return java.lang.String
     * @author 夕雾
     * @date 2024/9/24 19:32
     */
    String loginByEmail(String email,String password);
}
