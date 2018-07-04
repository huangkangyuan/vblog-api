package com.seu.blog.service.impl;

import com.seu.common.constant.CaptchaConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liangfeihu on 2018/6/27.
 */
@Service("captchaService")
public class CaptchaServiceImpl {
    @Resource
    RedisTemplate<String, String> kvLockTemplate;

    /**
     * 获取验证码
     *
     * @param key
     * @return
     */
    public String getCaptcha(String key) {
        return kvLockTemplate.opsForValue().get(CaptchaConstants.CAPTCHA_PREFIX + key);
    }

    /**
     * 设置验证码
     *
     * @param key
     * @param value
     * @return
     */
    public void setCaptcha(String key, String value) {
        kvLockTemplate.opsForValue().set(CaptchaConstants.CAPTCHA_PREFIX + key, value, 5, TimeUnit.MINUTES);
    }

    /**
     * 清除验证码
     *
     * @param key
     */
    public void delCaptcha(String key) {
        List<String> keys = new ArrayList<>();
        keys.add(CaptchaConstants.CAPTCHA_PREFIX + key);
        kvLockTemplate.delete(keys);
    }

}
