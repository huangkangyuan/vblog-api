package com.seu.blog.dao;

import com.seu.blog.entity.TagEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Mapper
public interface TagDao extends BaseMapper<TagEntity> {
    /**
     * 获取标签详情
     *
     * @param tagIds
     * @return
     */
    List<TagEntity> queryHotTagDetails(Integer[] tagIds);
}

