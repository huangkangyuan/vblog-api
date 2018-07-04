package com.seu.common.constant;

/**
 * Redis所有Keys
 *
 * @author liangfeihu
 * @email liangfeihu@163.com
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key) {
        return "sys:config:" + key;
    }

}
