package com.seu.blog.controller;

import com.seu.blog.entity.TagEntity;
import com.seu.blog.service.TagService;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;
import com.seu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@RestController
@RequestMapping("blog/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("blog:tag:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tagService.queryPage(params);

        return R.ok().put("page", page);
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
