package com.seu.blog.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章标签表
 * 
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@TableName("vblog_article_tag")
public class ArticleTagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Long id;
	/**
	 * 文章ID
	 */
	private Long articleId;
	/**
	 * 标签ID
	 */
	private Integer tagId;
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
	 * 设置：文章ID
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	/**
	 * 获取：文章ID
	 */
	public Long getArticleId() {
		return articleId;
	}
	/**
	 * 设置：标签ID
	 */
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	/**
	 * 获取：标签ID
	 */
	public Integer getTagId() {
		return tagId;
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
}
