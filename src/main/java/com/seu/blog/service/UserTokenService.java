package com.seu.blog.service;

import com.baomidou.mybatisplus.service.IService;
import com.seu.blog.entity.UserTokenEntity;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;

import java.util.Map;

/**
 * 系统用户Token
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
public interface UserTokenService extends IService<UserTokenEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 生成token
     *
     * @param userId 用户ID
     */
    R createToken(long userId);

    /**
     * 退出，修改token值
     *
     * @param userId 用户ID
     */
    void logout(long userId);
}

