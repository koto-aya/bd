package com.bd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bd.model.ChoiceAnswer;
import com.bd.model.Questions;
import com.bd.model.request.QuestionRequest;
import com.bd.model.request.QuestionSearchRequest;
import com.bd.model.vo.QuestionOptionsVo;
import com.bd.result.Result;
import com.bd.result.ResultEnum;
import com.bd.service.ChoiceAnswerService;
import com.bd.service.QuestionsService;
import com.bd.utils.LRUCache;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/21 20:19
 */
@RestController
@RequestMapping("/questions")
public class QuestionsController {
    @Autowired
    private QuestionsService questionsService;
    @Autowired
    private ChoiceAnswerService choiceAnswerService;

    private LRUCache<Long,QuestionOptionsVo> cache=new LRUCache<>(16,64);
    /**
     * @description: 添加题目
     * @param questions: 题目信息
     * @return com.bd.result.Result
     * @author 夕雾
     * @date 2024/9/21 20:19
     */
    @PostMapping("/add")
    public Result addQuestion(@RequestBody QuestionRequest questions){
        if (ObjectUtils.isEmpty(questions)){
            return Result.error(ResultEnum.ILLEGAL_PARAMETER);
        }
        String questionText = questions.getQuestionText();
        ChoiceAnswer choiceAnswer = questions.getChoiceAnswer();
        if (StringUtils.isBlank(questionText)||ObjectUtils.isEmpty(choiceAnswer)){
            return Result.error(ResultEnum.ILLEGAL_PARAMETER);
        }
        boolean isAdd=questionsService.add(questions);
        return isAdd?Result.ok():Result.fail();
    }

    /**
     * @description: 分页带条件获取题目
     * @param current: 当前页
     * @param limit: 每页记录数
     * @param searchCrite: 搜索条件
     * @return com.bd.result.Result<java.util.List<com.bd.model.Questions>>
     * @author 夕雾
     * @date 2024/9/21 23:03
     */
    @PostMapping("/get")
    public Result<Page<Questions>> getQuestions(@RequestParam Integer current, @RequestParam Integer limit,@RequestBody(required = false) QuestionSearchRequest searchCrite){
        if (current<1||limit<5){
            current=1;limit=5;
        }
        Page<Questions> data = questionsService.page(current, limit, searchCrite);
        return Result.ok(data);
    }

    /**
     * @description: 根据题目id获取选项及答案
     * @param questionId: 题目id
     * @return com.bd.result.Result<com.bd.model.vo.QuestionOptionsVo>
     * @author 夕雾
     * @date 2024/9/21 23:48
     */
    @GetMapping("/{questionId}")
    public Result<QuestionOptionsVo> showQuestionContent(@PathVariable("questionId")Long questionId){
        if (ObjectUtils.isEmpty(questionId)){
            return Result.fail();
        }
        //从缓存中取数据
        QuestionOptionsVo questionOptionsVo = cache.get(questionId);
        if (ObjectUtils.isNotEmpty(questionOptionsVo)){
            //点击数加1
            new Thread(() -> questionsService.increClickNum(questionId)).start();
            return Result.ok(questionOptionsVo);
        }
        //根据id获取题目标题
        String title=questionsService.getTitleById(questionId);
        //获取选项及答案
        QueryWrapper<ChoiceAnswer> wrapper=new QueryWrapper<>();
        wrapper.eq("question_id",questionId);
        ChoiceAnswer answer = choiceAnswerService.getOne(wrapper);

        QuestionOptionsVo result=new QuestionOptionsVo();
        BeanUtils.copyProperties(answer,result);
        result.setQuestionText(title);
        //将内容放到缓存中
        cache.put(questionId,result);
        //点击数加1
        questionsService.increClickNum(questionId);
        return Result.ok(result);
    }
}
