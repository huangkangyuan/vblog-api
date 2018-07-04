package com.seu.blog.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论表
 * 
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@TableName("vblog_comment")
public class CommentEntity implements Serializable {
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
	 * 文章ID
	 */
	private Long articleId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 父评论Id
	 */
	private Long parentId;
	/**
	 * 评论的评论用户ID
	 */
	private Long toUid;
	/**
	 * 评论级别
	 */
	private String levelFlag;
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
	 * 设置：评论内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：评论内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：父评论Id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父评论Id
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：评论的评论用户ID
	 */
	public void setToUid(Long toUid) {
		this.toUid = toUid;
	}
	/**
	 * 获取：评论的评论用户ID
	 */
	public Long getToUid() {
		return toUid;
	}
	/**
	 * 设置：评论级别
	 */
	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}
	/**
	 * 获取：评论级别
	 */
	public String getLevelFlag() {
		return levelFlag;
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
