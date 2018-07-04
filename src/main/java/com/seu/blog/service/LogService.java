package com.seu.blog.service;

import com.baomidou.mybatisplus.service.IService;
import com.seu.common.utils.PageUtils;
import com.seu.blog.entity.LogEntity;

import java.util.Map;

/**
 * 用户操作日志表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
public interface LogService extends IService<LogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

