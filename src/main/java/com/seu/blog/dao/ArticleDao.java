package com.seu.blog.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.vo.ArticleArchivesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@Mapper
public interface ArticleDao extends BaseMapper<ArticleEntity> {
    /**
     * 发布文章按年-月汇总
     *
     * @param limit
     * @return
     */
    List<ArticleArchivesVo> queyArticleArchives(int limit);
}

