package com.seu.blog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.LogDao;
import com.seu.blog.entity.LogEntity;
import com.seu.blog.service.LogService;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户操作日志表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Service("logService")
public class LogServiceImpl extends ServiceImpl<LogDao, LogEntity> implements LogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<LogEntity> page = this.selectPage(
                new Query<LogEntity>(params).getPage(),
                new EntityWrapper<LogEntity>()
        );

        return new PageUtils(page);
    }

}
