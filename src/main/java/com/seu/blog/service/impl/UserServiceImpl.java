package com.seu.blog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.UserDao;
import com.seu.blog.entity.UserEntity;
import com.seu.blog.service.UserService;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.Query;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 用户表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserEntity> page = this.selectPage(
                new Query<UserEntity>(params).getPage(),
                new EntityWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserEntity queryByUserAccount(String account) {
        return baseMapper.queryByUserAccount(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        this.insert(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        this.updateById(user);
    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.deleteBatchIds(Arrays.asList(userId));
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<UserEntity>().eq("user_id", userId).eq("password", password));
    }

}
