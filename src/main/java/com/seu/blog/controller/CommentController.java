package com.seu.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.seu.blog.dto.AuthorDto;
import com.seu.blog.dto.ChildCommentDto;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.entity.CommentEntity;
import com.seu.blog.entity.UserEntity;
import com.seu.blog.service.ArticleService;
import com.seu.blog.service.CommentService;
import com.seu.blog.service.UserService;
import com.seu.blog.vo.CommentVo;
import com.seu.common.component.R;
import com.seu.common.exception.RestException;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 评论表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@RestController
@RequestMapping("/comments")
public class CommentController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    /**
     * 获取某篇文章的评论
     */
    @RequestMapping("/article/{id}")
    public R info(@PathVariable("id") Long id) {
        List<CommentVo> commentVos = commentService.queryArticleComments(id);
        JSONArray array = new JSONArray();
        for (CommentVo vo : commentVos) {
            JSONObject object = formatCommentInfo(vo);

            EntityWrapper<CommentEntity> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("article_id", vo.getArticleId());
            entityWrapper.eq("parent_id", vo.getId());
            int childCount = commentService.selectCount(entityWrapper);
            if (childCount <= 0) {
                object.put("childrens", new JSONArray());
            } else {
                List<CommentVo> commentVoList = commentService.queryChildrenComments(new ChildCommentDto(vo.getArticleId(), vo.getId()));
                object.put("childrens", formatChildCommentInfo(commentVoList));
            }
            array.add(object);
        }
        return R.ok(array);
    }

    /**
     * 格式化评论数据
     * @param vo
     * @return
     */
    private JSONObject formatCommentInfo(CommentVo vo) {
        JSONObject object = new JSONObject();
        object.put("id", vo.getId());
        object.put("level", vo.getLevelFlag());
        object.put("content", vo.getContent());
        object.put("createDate", vo.getCreateTime().getTime());
        object.put("author", new AuthorDto(vo.getUserId(), vo.getAvatar(), vo.getNickname()));
        return object;
    }

    /**
     * 获取子评论信息
     *
     * @param commentVoList
     * @return
     */
    private JSONArray formatChildCommentInfo(List<CommentVo> commentVoList) {
        JSONArray array = new JSONArray();
        for (CommentVo vo : commentVoList) {
            JSONObject object = formatCommentInfo(vo);
            object.put("childrens", new JSONArray());
            if (vo.getToUid() != null) {
                UserEntity userEntity = userService.selectById(vo.getToUid());
                object.put("toUser", new AuthorDto(userEntity.getId(), userEntity.getAvatar(), userEntity.getNickname()));
            }
            array.add(object);
        }
        return array;
    }


    /**
     * 发表评论
     */
    @PostMapping("/create/change")
    public R save(@RequestBody JSONObject json) {
        UserEntity userEntity = ShiroUtils.getUserEntity();

        Long articleId = json.getJSONObject("article").getLong("id");
        ArticleEntity articleEntity = articleService.selectById(articleId);
        if (articleEntity == null) {
            throw new RestException("参数有误");
        }
        JSONObject object = commentService.publishArticleComment(articleEntity, userEntity, json);

        return R.ok(object);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = commentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        commentService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
