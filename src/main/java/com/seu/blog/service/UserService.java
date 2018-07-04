package com.seu.blog.service;

import com.baomidou.mybatisplus.service.IService;
import com.seu.common.utils.PageUtils;
import com.seu.blog.entity.UserEntity;

import java.util.Map;

/**
 * 用户表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据用户名，查询系统用户
     */
    UserEntity queryByUserAccount(String username);

    /**
     * 保存用户
     */
    void save(UserEntity user);

    /**
     * 修改用户
     */
    void update(UserEntity user);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);
}

