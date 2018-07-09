package com.seu.blog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.entity.UserEntity;
import com.seu.blog.vo.ArticleArchivesVo;

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

    /**
     * 发布文章按年-月汇总
     *
     * @param limit
     * @return
     */
    List<ArticleArchivesVo> queyArticleArchives(int limit);

    JSONArray getFormatArticleList(List<ArticleEntity> list);

    /**
     * 根据id获取文章，添加阅读数
     *
     * @param articleId
     * @return
     */
    JSONObject getArticleDetailAndAddViewNum(Long articleId);


    /**
     * 添加一篇新文章
     *
     * @param userEntity
     * @param json
     * @return
     */
    Long  addOneArticle(UserEntity userEntity, JSONObject json);

    /**
     * 更新一篇文章
     *
     * @param userEntity
     * @param article
     * @param json
     * @return
     */
    Long updateOneArticle(UserEntity userEntity, ArticleEntity article, JSONObject json);

}

