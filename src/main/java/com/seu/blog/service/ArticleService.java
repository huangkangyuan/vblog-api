package com.seu.blog.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.IService;
import com.seu.blog.entity.ArticleEntity;

import java.util.List;
import java.util.Map;

/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
public interface ArticleService extends IService<ArticleEntity> {

    List<ArticleEntity> queryPage(Map<String, Object> params);

    JSONArray getFormatArticleList(List<ArticleEntity> list);
}

