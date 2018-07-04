package com.seu.blog.controller;

import com.seu.blog.entity.CommentEntity;
import com.seu.blog.service.CommentService;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;
import com.seu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 评论表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@RestController
@RequestMapping("blog/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("blog:comment:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commentService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("blog:comment:info")
    public R info(@PathVariable("id") Long id){
        CommentEntity comment = commentService.selectById(id);

        return R.ok().put("comment", comment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("blog:comment:save")
    public R save(@RequestBody CommentEntity comment){
        commentService.insert(comment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("blog:comment:update")
    public R update(@RequestBody CommentEntity comment){
        ValidatorUtils.validateEntity(comment);
        //全部更新
        commentService.updateAllColumnById(comment);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("blog:comment:delete")
    public R delete(@RequestBody Long[] ids){
        commentService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
