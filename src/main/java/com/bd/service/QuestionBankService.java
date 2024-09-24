package com.bd.service;

import com.bd.model.QuestionBank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author kotoaya
* @description 针对表【question_bank(题库表)】的数据库操作Service
* @createDate 2024-09-21 00:06:21
*/
public interface QuestionBankService extends IService<QuestionBank> {

    /**
     * @description: 添加题库
     * @param questionBank: 题库信息
     * @return boolean
     * @author 夕雾
     * @date 2024/9/21 0:19
     */
    boolean add(QuestionBank questionBank);

    /**
     * @description: 根据分类获取该分类下的所有题库
     * @param category: 分类名
     * @return java.util.List<com.bd.model.QuestionBank>
     * @author 夕雾
     * @date 2024/9/21 18:14
     */
    List<QuestionBank> getBanksByCategory(String category);

}
