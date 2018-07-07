package com.seu.blog.service;

import com.baomidou.mybatisplus.service.IService;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.vo.TagPageVo;
import com.seu.common.utils.PageUtils;
import com.seu.blog.entity.ArticleTagEntity;

import java.util.List;
import java.util.Map;

/**
 * 文章标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
public interface ArticleTagService extends IService<ArticleTagEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询最热标签
     *
     * @param limit
     * @return
     */
    List<Integer> queryHotTagIds(Integer limit);

    /**
     * 根据标签查询文章
     *
     * @param tagPageVo
     * @return
     */
    List<ArticleEntity> queryArticlesByTag(TagPageVo tagPageVo);
}

