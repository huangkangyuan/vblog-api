package com.seu.blog.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章类别表
 * 
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@TableName("vblog_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Integer id;
	/**
	 * 类别名字
	 */
	private String categoryName;
	/**
	 * 类别图标
	 */
	private String avatar;
	/**
	 * 分类描述
	 */
	private String description;
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
	 * 设置：类别名字
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取：类别名字
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置：类别图标
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	/**
	 * 获取：类别图标
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * 设置：分类描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：分类描述
	 */
	public String getDescription() {
		return description;
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
