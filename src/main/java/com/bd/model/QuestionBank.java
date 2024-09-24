package com.bd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 题库表
 * @TableName question_bank
 */
@TableName(value ="question_bank")
@Data
public class QuestionBank implements Serializable {
    /**
     * 通过雪花算法生成的id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 题库名称
     */
    private String bankName;

    /**
     * 题库类型
     */
    private Integer bankCategory;

    /**
     * 图标链接
     */
    private String iconUrl;

    /**
     * 题库描述
     */
    private String bankDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}