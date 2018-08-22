package com.seu.common.validator;

import com.seu.common.exception.RestException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 * @author liangfeihu
 * @email liangfeihu@163.com
 * @date 2017-03-23 15:50
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RestException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RestException(message);
        }
    }
}
