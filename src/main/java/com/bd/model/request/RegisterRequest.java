package com.bd.model.request;

import lombok.Data;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/23 23:10
 */
@Data
public class RegisterRequest {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String email;
    private String cellPhoneNum;
}
