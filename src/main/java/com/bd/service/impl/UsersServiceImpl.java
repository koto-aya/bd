package com.bd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bd.model.constant.Patterns;
import com.bd.model.constant.UserConstant;
import com.bd.exception.XiwuReadException;
import com.bd.model.User;
import com.bd.model.request.RegisterRequest;
import com.bd.result.ResultEnum;
import com.bd.service.UsersService;
import com.bd.mapper.UsersMapper;
import com.bd.utils.JWTUtil;
import com.bd.utils.RandomUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;

/**
* @author kotoaya
* @description 针对表【users(用户表)】的数据库操作Service实现
* @createDate 2024-09-23 10:16:03
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, User>
    implements UsersService, UserConstant {
    //用户账户要大于等于8位小于16位
    //用户账户不能包含特殊字符
    //用户账户只能包含中文日文英文
    //密码和确认密码长度要大于等于8
    //密码必须包含字母和数字
    //密码和确认密码要相同
    @Override
    public boolean register(RegisterRequest request) {
        if (ObjectUtils.isEmpty(request))
            throw new XiwuReadException(ResultEnum.FAIL);
        String userAccount = request.getUserAccount();
        String userPassword = request.getUserPassword();
        String checkPassword = request.getCheckPassword();
        String email = request.getEmail();
        String cellPhoneNum = request.getCellPhoneNum();
        //用户账户要大于等于8位小于16位
        if (userAccount.length()<=8 || userAccount.length()>16)
            throw new XiwuReadException(ResultEnum.ACCOUNT_TOO_SHORT);
        //用户账户不能包含特殊字符
        //用户账户只能包含中文日文英文
        if(!userAccount.matches(Patterns.USER_ACCOUNT.getRegEx()))
            throw new XiwuReadException(ResultEnum.CANNOT_CONTAIN_SPECIAL_CHAR);
        //密码复杂度校验
        if (!userPassword.matches(Patterns.PASSWORD.getRegEx()))
            throw new XiwuReadException(ResultEnum.SIMPLE_PASSWORD);
        if (!StringUtils.equals(userPassword,checkPassword))
            throw new XiwuReadException(ResultEnum.PASSWORD_INCONSISTENCY);
        //用户存在校验
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("user_account",userAccount);
        Long exist = baseMapper.selectCount(wrapper);
        if (exist>0)
            throw new XiwuReadException(ResultEnum.USER_ALREADY_EXIST);
        //密码加密
        String encryPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());
        //保存数据
        User user=new User();
        //设置用户昵称
        user.setNickName(RandomUtil.generateStr(6));
        BeanUtils.copyProperties(request,user);
        user.setUserPassword(encryPassword);
        int insert = baseMapper.insert(user);
        return insert>0;
    }

    //手机登录自动注册账号
    @Override
    public String loginByMobile(String mobileNum, String checkCode) {
        if (!mobileNum.matches(Patterns.MOBILE.getRegEx())){
            throw new XiwuReadException(ResultEnum.ILLEGAL_CELL_PHONE_NUM);
        }
        //校验是否注册
        QueryWrapper<User> checkNewWrapper=new QueryWrapper<>();
        checkNewWrapper.eq("cell_phone_num",mobileNum).or(true).eq("user_account",mobileNum);
        Long exist = baseMapper.selectCount(checkNewWrapper);
        if (exist==0){
            throw new XiwuReadException(ResultEnum.USER_NOT_EXIST);
        }
        //todo 校验验证码
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("cell_phone_num",mobileNum);
        String token = login(wrapper);
        return token;
    }

    @Override
    public String loginByUserAccount(String userAccount, String password) {
        if (!userAccount.matches(Patterns.USER_ACCOUNT.getRegEx())||!password.matches(Patterns.PASSWORD.getRegEx())){
            throw new XiwuReadException(ResultEnum.INCORRECT_ACCOUNT_OR_PASSWORD);
        }
        String encryPassword = DigestUtils.md5DigestAsHex((password + SALT).getBytes());
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("user_account",userAccount);
        wrapper.eq("user_password",encryPassword);
        String token = login(wrapper);
        return token;
    }

    @Override
    public String loginByEmail(String email, String password) {
        return "暂未实现";
    }


    private String login(QueryWrapper<User> wrapper){
        if (ObjectUtils.isEmpty(wrapper)){
            throw new XiwuReadException(ResultEnum.FAIL);
        }
        wrapper.select("user_account");
        User user = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(user)||StringUtils.isBlank(user.getUserAccount())){
            throw new XiwuReadException(ResultEnum.INCORRECT_ACCOUNT_OR_PASSWORD);
        }
        String userAccount=user.getUserAccount();
        //生成token
        HashMap<String,Object> claim=new HashMap<>();
        claim.put("user_account",userAccount);
        String token = JWTUtil.generate(claim);
        return token;
    }
}




