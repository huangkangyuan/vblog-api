package com.seu.blog.dao;

import com.seu.blog.entity.UserTokenEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Mapper
public interface UserTokenDao extends BaseMapper<UserTokenEntity> {

    UserTokenEntity queryByToken(String token);

}

