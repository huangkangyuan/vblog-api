package com.seu.blog.controller;

import com.seu.blog.entity.TagEntity;
import com.seu.blog.service.TagService;
import com.seu.common.component.R;
import com.seu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(){
        List<TagEntity> tagEntities = tagService.selectList(null);
        return R.ok(tagEntities);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("blog:tag:info")
    public R info(@PathVariable("id") Integer id){
        TagEntity tag = tagService.selectById(id);

        return R.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("blog:tag:save")
    public R save(@RequestBody TagEntity tag){
        tagService.insert(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("blog:tag:update")
    public R update(@RequestBody TagEntity tag){
        ValidatorUtils.validateEntity(tag);
        //全部更新
        tagService.updateAllColumnById(tag);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("blog:tag:delete")
    public R delete(@RequestBody Integer[] ids){
        tagService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
