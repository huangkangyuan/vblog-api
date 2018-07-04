package com.seu.blog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.ArticleTagDao;
import com.seu.blog.entity.ArticleTagEntity;
import com.seu.blog.service.ArticleTagService;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 文章标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTagEntity> implements ArticleTagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ArticleTagEntity> page = this.selectPage(
                new Query<ArticleTagEntity>(params).getPage(),
                new EntityWrapper<ArticleTagEntity>()
        );

        return new PageUtils(page);
    }

}
