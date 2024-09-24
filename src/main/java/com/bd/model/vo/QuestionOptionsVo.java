package com.bd.model.vo;

import lombok.Data;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/21 23:35
 */
@Data
public class QuestionOptionsVo {
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private String analysis;
}
