package com.seu.blog.dao;

import com.seu.blog.entity.LogEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户操作日志表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Mapper
public interface LogDao extends BaseMapper<LogEntity> {

}

