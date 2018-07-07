package com.seu.blog.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.entity.ArticleTagEntity;
import com.seu.blog.vo.TagPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@Mapper
public interface ArticleTagDao extends BaseMapper<ArticleTagEntity> {

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

