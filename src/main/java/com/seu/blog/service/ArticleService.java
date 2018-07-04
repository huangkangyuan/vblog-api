package com.seu.blog.service;

import com.baomidou.mybatisplus.service.IService;
import com.seu.common.utils.PageUtils;
import com.seu.blog.entity.ArticleEntity;

import java.util.Map;

/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
public interface ArticleService extends IService<ArticleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

