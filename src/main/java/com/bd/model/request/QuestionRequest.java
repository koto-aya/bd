package com.bd.model.request;

import com.bd.model.ChoiceAnswer;
import lombok.Data;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/21 21:31
 */
@Data
public class QuestionRequest {
    /**
     * 题目
     */
    private String questionText;

    /**
     * 题目难度
     */
    private String difficult;

    /**
     * 所属题库id
     */
    private Integer fromBankId;

    /**
     * 标签
     */
    private String tags;

    /**
     * 题目内嵌图片地址
     */
    private String embedImageUrl;

    /**
     * 答案信息
     */
    private ChoiceAnswer choiceAnswer;
}
