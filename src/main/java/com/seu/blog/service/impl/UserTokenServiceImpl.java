package com.seu.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.UserTokenDao;
import com.seu.blog.entity.UserTokenEntity;
import com.seu.blog.service.UserTokenService;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.Query;
import com.seu.shiro.TokenGenerator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 系统用户Token
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Service("userTokenService")
public class UserTokenServiceImpl extends ServiceImpl<UserTokenDao, UserTokenEntity> implements UserTokenService {
    /**
     * 7天后过期 单位秒
     */
    private final static int EXPIRE = 3600 * 24 * 7;


    @Override
    public R createToken(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        UserTokenEntity tokenEntity = this.selectById(userId);
        if (tokenEntity == null) {
            tokenEntity = new UserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setCreateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            this.insert(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            this.updateById(tokenEntity);
        }

        JSONObject object = new JSONObject();
        object.put("Oauth-Token", token);
        object.put("expire", EXPIRE);

        R r = R.ok(object);

        return r;
    }

    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        UserTokenEntity tokenEntity = new UserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        tokenEntity.setUpdateTime(new Date());
        this.updateById(tokenEntity);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserTokenEntity> page = this.selectPage(
                new Query<UserTokenEntity>(params).getPage(),
                new EntityWrapper<UserTokenEntity>()
        );

        return new PageUtils(page);
    }

}
