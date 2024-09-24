package com.bd.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bd.model.Questions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bd.model.request.QuestionRequest;
import com.bd.model.request.QuestionSearchRequest;

/**
* @author kotoaya
* @description 针对表【questions(题目表)】的数据库操作Service
* @createDate 2024-09-21 00:06:24
*/
public interface QuestionsService extends IService<Questions> {
    /**
     * @description: 添加题目
     * @param question: 题目信息
     * @return boolean
     * @author 夕雾
     * @date 2024/9/21 21:05
     */
    boolean add(QuestionRequest question);
    /**
     * @param current: 当前页
     * @param limit:   每页记录数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<java.util.List < com.bd.model.Questions>>
     * @description: 分页获取题目数据
     * @author 夕雾
     * @date 2024/9/21 23:13
     */
    Page<Questions> page(int current, int limit);
    /**
     * @param current:     当前页
     * @param limit:       每页记录数
     * @param searchCrite: 搜索条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<java.util.List < com.bd.model.Questions>>
     * @description: 分页带条件获取题目数据
     * @author 夕雾
     * @date 2024/9/21 23:13
     */
    Page<Questions> page(int current, int limit, QuestionSearchRequest searchCrite);

    /**
     * @description: 根据题目id获取题目标题
     * @param questionId: 题目id
     * @return java.lang.String
     * @author 夕雾
     * @date 2024/9/21 23:42
     */
    String getTitleById(long questionId);

    /**
     * @description: 增加题目的点击数
     * @param questionId: 题目id
     * @return void
     * @author 夕雾
     * @date 2024/9/22 16:08
     */
    void increClickNum(Long questionId);
}
