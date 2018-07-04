package com.seu.blog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.ArticleDao;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.service.ArticleService;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ArticleEntity> page = this.selectPage(
                new Query<ArticleEntity>(params).getPage(),
                new EntityWrapper<ArticleEntity>()
        );

        return new PageUtils(page);
    }

}
