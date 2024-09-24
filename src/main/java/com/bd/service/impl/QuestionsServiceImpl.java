package com.bd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bd.exception.XiwuReadException;
import com.bd.model.ChoiceAnswer;
import com.bd.model.QuestionBank;
import com.bd.model.Questions;
import com.bd.model.request.QuestionRequest;
import com.bd.model.request.QuestionSearchRequest;
import com.bd.result.ResultEnum;
import com.bd.service.ChoiceAnswerService;
import com.bd.service.QuestionBankService;
import com.bd.service.QuestionsService;
import com.bd.mapper.QuestionsMapper;
import com.bd.utils.LRUCache;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

/**
* @author kotoaya
* @description 针对表【questions(题目表)】的数据库操作Service实现
* @createDate 2024-09-21 00:06:24
*/
@Service
@Transactional
public class QuestionsServiceImpl extends ServiceImpl<QuestionsMapper, Questions>
    implements QuestionsService {
    @Autowired
    private QuestionBankService bankService;
    @Autowired
    private ChoiceAnswerService answerService;

    private LRUCache<Long,Integer> cache=new LRUCache<>(4,16);

    @Override
    public boolean add(QuestionRequest questionRequest) {
        String questionText = questionRequest.getQuestionText();
        Integer fromBankId = questionRequest.getFromBankId();
        String embedImageUrl = questionRequest.getEmbedImageUrl();
        if (StringUtils.isBlank(questionText))
            throw new XiwuReadException(ResultEnum.FAIL);
        //图片链接若存在，则判断其是否合法
        if (StringUtils.isNotBlank(embedImageUrl)&&!StringUtils.startsWithAny(embedImageUrl, "http://", "https://"))
            throw new XiwuReadException(ResultEnum.INACCESSIBLE_IMAGE_LINK);
        //查询所属题库是否存在
        QuestionBank bank = bankService.getById(fromBankId);
        if (ObjectUtils.isEmpty(bank))
            throw new XiwuReadException(ResultEnum.BANK_NOT_FOUND);
        //保存题目信息，并返回题目id
        Questions question=new Questions();
        BeanUtils.copyProperties(questionRequest,question);
        int isInsert = baseMapper.insert(question);
        if (isInsert==0){
            throw new XiwuReadException(ResultEnum.FAIL);
        }
        long generateQuestionId=question.getId();//由MP自动生成的id
        //保存答案
        ChoiceAnswer choiceAnswer = questionRequest.getChoiceAnswer();
        Long questionId = generateQuestionId;
        choiceAnswer.setQuestionId(questionId);
        String optionA = choiceAnswer.getOptionA();
        String optionB = choiceAnswer.getOptionB();
        String optionC = choiceAnswer.getOptionC();
        String optionD = choiceAnswer.getOptionD();
        if (StringUtils.isAnyBlank(optionA,optionB,optionD,optionC))
            throw new XiwuReadException(ResultEnum.CHOICE_QUESTION_OPTION_TOO_FEW);
        String correctAnswer = choiceAnswer.getCorrectAnswer();
        //正确答案必须为a、b、c、d其中一个
        if (!StringUtils.containsAnyIgnoreCase(correctAnswer,"a","b","c","d"))
            throw new XiwuReadException(ResultEnum.FAIL);
        String analysis = choiceAnswer.getAnalysis();
        boolean isSave = answerService.save(choiceAnswer);
        return isSave;
    }

    @Override
    public Page<Questions> page(int current, int limit) {
        return page(current,limit,null);
    }

    @Override
    public Page<Questions> page(int current, int limit, QuestionSearchRequest searchCrite) {
        Page<Questions> page=new Page<>(current,limit);
        if (ObjectUtils.isEmpty(searchCrite)){
            return baseMapper.selectPage(page,null);
        }
        QueryWrapper<Questions> wrapper=new QueryWrapper<>();
        String fromBankId = searchCrite.getFromBankId();
        String difficult = searchCrite.getDifficult();
        Boolean orderByClick = searchCrite.isOrderByClick();
        if (StringUtils.isNotBlank(fromBankId))
            wrapper.eq("from_bank_id",fromBankId);
        if (StringUtils.isNotBlank(difficult))
            wrapper.eq("difficult",difficult);
        if (ObjectUtils.isNotEmpty(orderByClick))
            wrapper.orderBy(orderByClick,false,"click_num");
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public String getTitleById(long questionId) {
        QueryWrapper<Questions> wrapper=new QueryWrapper<>();
        wrapper.select("question_text").eq("id",questionId);
        Questions questions = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(questions)){
            throw new XiwuReadException(ResultEnum.QUESTION_NOT_FOUND);
        }
        String title = questions.getQuestionText();
        return title;
    }

    @Override
    public synchronized void increClickNum(Long questionId) {
        QueryWrapper<Questions> wrapper=new QueryWrapper<>();
        wrapper.eq("id",questionId);
        //获取当前的点击数
        Questions questions = baseMapper.selectOne(wrapper);
        int currentClickNum= questions.getClickNum()+1;
        //增加
        questions.setClickNum(currentClickNum);
        baseMapper.update(questions,wrapper);
    }
}