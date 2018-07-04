package com.seu.blog.controller;

import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.service.ArticleService;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;
import com.seu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@RestController
@RequestMapping("blog/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("blog:article:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = articleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("blog:article:info")
    public R info(@PathVariable("id") Long id){
        ArticleEntity article = articleService.selectById(id);

        return R.ok().put("article", article);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("blog:article:save")
    public R save(@RequestBody ArticleEntity article){
        articleService.insert(article);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("blog:article:update")
    public R update(@RequestBody ArticleEntity article){
        ValidatorUtils.validateEntity(article);
        //全部更新
        articleService.updateAllColumnById(article);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("blog:article:delete")
    public R delete(@RequestBody Long[] ids){
        articleService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
