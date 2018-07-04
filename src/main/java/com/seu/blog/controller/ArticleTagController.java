package com.seu.blog.controller;

import com.seu.blog.entity.ArticleTagEntity;
import com.seu.blog.service.ArticleTagService;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;
import com.seu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 文章标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@RestController
@RequestMapping("blog/articletag")
public class ArticleTagController {
    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("blog:articletag:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = articleTagService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("blog:articletag:info")
    public R info(@PathVariable("id") Long id){
        ArticleTagEntity articleTag = articleTagService.selectById(id);

        return R.ok().put("articleTag", articleTag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("blog:articletag:save")
    public R save(@RequestBody ArticleTagEntity articleTag){
        articleTagService.insert(articleTag);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("blog:articletag:update")
    public R update(@RequestBody ArticleTagEntity articleTag){
        ValidatorUtils.validateEntity(articleTag);
        //全部更新
        articleTagService.updateAllColumnById(articleTag);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("blog:articletag:delete")
    public R delete(@RequestBody Long[] ids){
        articleTagService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
