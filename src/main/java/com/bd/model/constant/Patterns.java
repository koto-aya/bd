package com.bd.model.constant;

import lombok.Getter;

@Getter
public enum Patterns {
    MOBILE("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$"),
    USER_ACCOUNT("[\u30a1-\u30f6\u3041-\u3093\uFF00-\uFFFF\u4e00-\u9fa5 \u4e00-\u9fa5a-zA-Z0-9]+"),
    PASSWORD("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$");

    private String regEx;
    Patterns(String regEx){
        this.regEx=regEx;
    }
}
