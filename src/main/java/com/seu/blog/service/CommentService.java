package com.seu.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.seu.blog.dto.ChildCommentDto;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.entity.UserEntity;
import com.seu.blog.vo.CommentVo;
import com.seu.common.utils.PageUtils;
import com.seu.blog.entity.CommentEntity;

import java.util.List;
import java.util.Map;

/**
 * 评论表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取文章评论信息
     *
     * @param articleId
     * @return
     */
    List<CommentVo> queryArticleComments(Long articleId);

    /**
     * 获取子评论信息
     *
     * @param dto
     * @return
     */
    List<CommentVo> queryChildrenComments(ChildCommentDto dto);

    /**
     * 发布文章评论
     *
     * @param articleEntity
     * @param userEntity
     * @param json
     * @return
     */
    JSONObject publishArticleComment(ArticleEntity articleEntity, UserEntity userEntity, JSONObject json);
}

