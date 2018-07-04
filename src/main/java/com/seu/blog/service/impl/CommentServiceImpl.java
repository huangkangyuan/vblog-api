package com.seu.blog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.CommentDao;
import com.seu.blog.entity.CommentEntity;
import com.seu.blog.service.CommentService;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 评论表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CommentEntity> page = this.selectPage(
                new Query<CommentEntity>(params).getPage(),
                new EntityWrapper<CommentEntity>()
        );

        return new PageUtils(page);
    }

}
