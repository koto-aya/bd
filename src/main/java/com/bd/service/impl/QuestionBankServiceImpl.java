package com.bd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bd.exception.XiwuReadException;
import com.bd.model.BankCategory;
import com.bd.model.QuestionBank;
import com.bd.result.ResultEnum;
import com.bd.service.BankCategoryService;
import com.bd.service.QuestionBankService;
import com.bd.mapper.QuestionBankMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author kotoaya
* @description 针对表【question_bank(题库表)】的数据库操作Service实现
* @createDate 2024-09-21 00:06:21
*/
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank>
    implements QuestionBankService{

    @Autowired
    private BankCategoryService bankCategoryService;

    @Override
    public boolean add(QuestionBank questionBank) {
        String bankName = questionBank.getBankName();
        Integer bankType = questionBank.getBankCategory();
        String iconUrl = questionBank.getIconUrl();
        String bankDesc = questionBank.getBankDesc();
        if (StringUtils.isBlank(bankName)){
            throw new XiwuReadException(ResultEnum.FAIL);
        }
        //todo 校验type
        int insert = baseMapper.insert(questionBank);
        return insert>0;
    }

    @Override
    public List<QuestionBank> getBanksByCategory(String category) {
        if (StringUtils.isBlank(category)){
            throw new XiwuReadException(ResultEnum.FAIL);
        }
        //查找是否有该分类
        QueryWrapper<BankCategory> categoryFindWrapper=new QueryWrapper<>();
        categoryFindWrapper.eq("category_code",category);
        long hitCategory = bankCategoryService.count(categoryFindWrapper);
        if (hitCategory==0){
            throw new XiwuReadException(ResultEnum.CATEGORY_NOT_FOUND);
        }
        //根据分类代码查找题库信息
        QueryWrapper<QuestionBank> bankWrapper=new QueryWrapper<>();
        bankWrapper.eq("bank_category",category);
        List<QuestionBank> data = baseMapper.selectList(bankWrapper);
        return data;
    }
}




