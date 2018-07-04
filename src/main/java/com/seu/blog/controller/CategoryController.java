package com.seu.blog.controller;

import com.seu.blog.entity.CategoryEntity;
import com.seu.blog.service.CategoryService;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;
import com.seu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 文章类别表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@RestController
@RequestMapping("blog/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("blog:category:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("blog:category:info")
    public R info(@PathVariable("id") Integer id){
        CategoryEntity category = categoryService.selectById(id);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("blog:category:save")
    public R save(@RequestBody CategoryEntity category){
        categoryService.insert(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("blog:category:update")
    public R update(@RequestBody CategoryEntity category){
        ValidatorUtils.validateEntity(category);
        //全部更新
        categoryService.updateAllColumnById(category);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("blog:category:delete")
    public R delete(@RequestBody Integer[] ids){
        categoryService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
