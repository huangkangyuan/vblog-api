package com.seu.blog.dao;

import com.seu.blog.entity.ArticleTagEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@Mapper
public interface ArticleTagDao extends BaseMapper<ArticleTagEntity> {

}

