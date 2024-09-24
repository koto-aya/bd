package com.bd.controller;

import com.bd.model.QuestionBank;
import com.bd.result.Result;
import com.bd.service.QuestionBankService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/21 0:12
 */
@RestController
@RequestMapping("/")
public class QuestionBankController {
    @Autowired
    private QuestionBankService bankService;
    /**
     * @description: 添加题库
     * @param questionBank: 题库信息
     * @return com.bd.result.Result<java.lang.Object>
     * @author 夕雾
     * @date 2024/9/21 0:16
     */
    @PostMapping("bank/add")
    public Result<Object> addBank(@RequestBody QuestionBank questionBank){
        if (ObjectUtils.isEmpty(questionBank)){
            return Result.fail("题库信息不完整");
        }
        boolean res=bankService.add(questionBank);
        return res?Result.ok():Result.fail();
    }

    /**
     * @description: 根据分类编号获取该分类下的所有题库
     * @param category: 分类编号
     * @return com.bd.result.Result<java.util.List<com.bd.model.QuestionBank>>
     * @author 夕雾
     * @date 2024/9/21 20:03
     */
    @GetMapping("banks")
    public Result<List<QuestionBank>> getBank(@RequestParam(required = false) String category){
        //如果没有分类编号就查询所有
        if (StringUtils.isBlank(category)){
            return Result.ok(bankService.list());
        }
        List<QuestionBank> list=bankService.getBanksByCategory(category);
        return Result.ok(list);
    }

}
