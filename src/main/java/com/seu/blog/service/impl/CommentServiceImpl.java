package com.seu.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.CommentDao;
import com.seu.blog.dto.AuthorDto;
import com.seu.blog.dto.ChildCommentDto;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.entity.CommentEntity;
import com.seu.blog.entity.UserEntity;
import com.seu.blog.service.ArticleService;
import com.seu.blog.service.CommentService;
import com.seu.blog.vo.CommentVo;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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
    @Autowired
    private ArticleService articleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CommentEntity> page = this.selectPage(
                new Query<CommentEntity>(params).getPage(),
                new EntityWrapper<CommentEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取文章评论信息
     *
     * @param articleId
     * @return
     */
    @Override
    public List<CommentVo> queryArticleComments(Long articleId) {
        return this.baseMapper.queryArticleComments(articleId);
    }

    /**
     * 获取子评论信息
     *
     * @param dto
     * @return
     */
    @Override
    public List<CommentVo> queryChildrenComments(ChildCommentDto dto) {
        return this.baseMapper.queryChildrenComments(dto);
    }

    /**
     * 发布文章评论
     *
     * @param articleEntity
     * @param userEntity
     * @param json
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject publishArticleComment(ArticleEntity articleEntity, UserEntity userEntity, JSONObject json) {
        // 1、更新文章评论数+1
        articleEntity.setCommentNum(articleEntity.getCommentNum() + 1);
        articleService.updateById(articleEntity);

        // 2、插入一条文章评论
        CommentEntity comment = new CommentEntity();
        comment.setArticleId(articleEntity.getId());
        comment.setContent(json.getString("content"));
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setUserId(userEntity.getId());

        // 3、设置文章评论级别 level_flag
        //    为0:评论文章;1:评论某人评论;2:回复某人评论
        JSONObject parent = json.getJSONObject("parent");
        JSONObject toUser = json.getJSONObject("toUser");
        if (parent != null) {
            comment.setParentId(parent.getLong("id"));
            if (toUser != null) {
                comment.setLevelFlag("2");
                comment.setToUid(toUser.getLong("id"));
            } else {
                comment.setLevelFlag("1");
            }
        } else {
            comment.setLevelFlag("0");
        }
        this.insert(comment);

        // 4、返回评论数据
        JSONObject object = new JSONObject();
        object.put("id", comment.getId());
        object.put("level", comment.getLevelFlag());
        object.put("content", comment.getContent());
        object.put("createDate", comment.getCreateTime().getTime());
        object.put("author", new AuthorDto(comment.getUserId(), userEntity.getAvatar(), userEntity.getNickname()));
        if (toUser != null) {
            object.put("toUser", toUser);
        }
        if (parent != null) {
            object.put("parent", parent);
        }

        return object;
    }
}
