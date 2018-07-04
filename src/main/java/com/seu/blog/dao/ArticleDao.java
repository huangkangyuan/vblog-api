package com.seu.blog.dao;

import com.seu.blog.entity.ArticleEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@Mapper
public interface ArticleDao extends BaseMapper<ArticleEntity> {

}

