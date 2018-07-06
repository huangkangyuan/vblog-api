package com.seu.blog.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@TableName("vblog_article")
public class ArticleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 作者昵称
     */
    private String nickname;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 文章内容txt
     */
    private String content;
    /**
     * 文章内容html
     */
    private String contentHtml;
    /**
     * 浏览数
     */
    private Integer viewNum;
    /**
     * 评论数
     */
    private Integer commentNum;
    /**
     * 权重
     */
    private Integer weight;
    /**
     * 文章标签
     */
    private String tags;
    /**
     * 文章分类ID
     */
    private Integer categoryId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 设置：主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：文章标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：文章标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置：文章摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取：文章摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置：文章内容txt
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：文章内容txt
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置：文章内容html
     */
    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    /**
     * 获取：文章内容html
     */
    public String getContentHtml() {
        return contentHtml;
    }

    /**
     * 设置：浏览数
     */
    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    /**
     * 获取：浏览数
     */
    public Integer getViewNum() {
        return viewNum;
    }

    /**
     * 设置：评论数
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * 获取：评论数
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * 设置：权重
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 获取：权重
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * 设置：文章分类ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取：文章分类ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String[] getTags() {
        return tags!= null ? tags.split(",") : null;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
