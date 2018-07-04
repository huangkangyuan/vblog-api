package com.seu.blog.dao;

import com.seu.blog.entity.TagEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Mapper
public interface TagDao extends BaseMapper<TagEntity> {

}

