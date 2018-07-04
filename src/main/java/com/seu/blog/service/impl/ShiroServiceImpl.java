package com.seu.blog.service.impl;

import com.seu.blog.dao.UserDao;
import com.seu.blog.dao.UserTokenDao;
import com.seu.blog.entity.UserEntity;
import com.seu.blog.entity.UserTokenEntity;
import com.seu.blog.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * shiro权限service实现类
 *
 * @author liangfeihu
 * @since 2018/6/26 14:37.
 */
@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserTokenDao userTokenDao;

    @Override
    public Set<String> getUserPermissions(long userId) {
        return null;
    }

    @Override
    public UserTokenEntity queryByToken(String token) {
        return userTokenDao.queryByToken(token);
    }

    @Override
    public UserEntity queryUser(Long userId) {
        return userDao.selectById(userId);
    }
}
