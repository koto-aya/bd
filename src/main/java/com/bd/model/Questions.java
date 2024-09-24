package com.bd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 题目表
 * @TableName questions
 */
@TableName(value ="questions")
@Data
public class Questions implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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
    private Long fromBankId;

    /**
     * 收藏数量
     */
    private Integer favorityNum;

    /**
     * 评论数量
     */
    private Integer commentNum;

    /**
     * 点击数量
     */
    private Integer clickNum;

    /**
     * 标签
     */
    private String tags;

    /**
     * 题目内嵌图片地址
     */
    private String embedImageUrl;

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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Questions other = (Questions) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getQuestionText() == null ? other.getQuestionText() == null : this.getQuestionText().equals(other.getQuestionText()))
            && (this.getDifficult() == null ? other.getDifficult() == null : this.getDifficult().equals(other.getDifficult()))
            && (this.getFromBankId() == null ? other.getFromBankId() == null : this.getFromBankId().equals(other.getFromBankId()))
            && (this.getFavorityNum() == null ? other.getFavorityNum() == null : this.getFavorityNum().equals(other.getFavorityNum()))
            && (this.getCommentNum() == null ? other.getCommentNum() == null : this.getCommentNum().equals(other.getCommentNum()))
            && (this.getClickNum() == null ? other.getClickNum() == null : this.getClickNum().equals(other.getClickNum()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getQuestionText() == null) ? 0 : getQuestionText().hashCode());
        result = prime * result + ((getDifficult() == null) ? 0 : getDifficult().hashCode());
        result = prime * result + ((getFromBankId() == null) ? 0 : getFromBankId().hashCode());
        result = prime * result + ((getFavorityNum() == null) ? 0 : getFavorityNum().hashCode());
        result = prime * result + ((getCommentNum() == null) ? 0 : getCommentNum().hashCode());
        result = prime * result + ((getClickNum() == null) ? 0 : getClickNum().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", questionText=").append(questionText);
        sb.append(", difficult=").append(difficult);
        sb.append(", fromBankId=").append(fromBankId);
        sb.append(", favorityNum=").append(favorityNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", clickNum=").append(clickNum);
        sb.append(", tags=").append(tags);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}