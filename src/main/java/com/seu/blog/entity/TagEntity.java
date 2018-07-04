package com.seu.blog.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签表
 * 
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@TableName("vblog_tag")
public class TagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Integer id;
	/**
	 * 标签名字
	 */
	private String tagName;
	/**
	 * 标签图标
	 */
	private String avatar;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：标签名字
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	/**
	 * 获取：标签名字
	 */
	public String getTagName() {
		return tagName;
	}
	/**
	 * 设置：标签图标
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	/**
	 * 获取：标签图标
	 */
	public String getAvatar() {
		return avatar;
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
}
