package com.seu.blog.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户操作日志表
 * 
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@TableName("vblog_log")
public class LogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Long id;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 访问Ip
	 */
	private String ip;
	/**
	 * 操作模块
	 */
	private String module;
	/**
	 * 访问方法
	 */
	private String method;
	/**
	 * 方法参数
	 */
	private String params;
	/**
	 * 操作人昵称
	 */
	private String nickname;
	/**
	 * 操作事项
	 */
	private String operation;
	/**
	 * 操作耗时
	 */
	private Long time;
	/**
	 * 操作用户userId
	 */
	private Long userId;

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
	 * 设置：访问Ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：访问Ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置：操作模块
	 */
	public void setModule(String module) {
		this.module = module;
	}
	/**
	 * 获取：操作模块
	 */
	public String getModule() {
		return module;
	}
	/**
	 * 设置：访问方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：访问方法
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：方法参数
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 获取：方法参数
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 设置：操作人昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：操作人昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：操作事项
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * 获取：操作事项
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * 设置：操作耗时
	 */
	public void setTime(Long time) {
		this.time = time;
	}
	/**
	 * 获取：操作耗时
	 */
	public Long getTime() {
		return time;
	}
	/**
	 * 设置：操作用户userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：操作用户userId
	 */
	public Long getUserId() {
		return userId;
	}
}
