package com.bd.model.request;

import lombok.Data;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/21 22:59
 */
@Data
public class QuestionSearchRequest {
    private String fromBankId;//所属库id
    private String difficult;//难度
    private boolean orderByClick;//是否排序
}
