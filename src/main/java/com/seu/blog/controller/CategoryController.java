package com.seu.blog.controller;

import com.seu.blog.entity.CategoryEntity;
import com.seu.blog.service.CategoryService;
import com.seu.blog.vo.CategoryVo;
import com.seu.common.component.R;
import com.seu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 文章类别表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        List<CategoryEntity> categoryEntityList = categoryService.selectList(null);
        return R.ok(categoryEntityList);
    }

    /**
     * 分类详情
     */
    @GetMapping("/detail")
    public R detail(){
        List<CategoryVo> categoryVos = categoryService.queryCategoryDetails();
        return R.ok(categoryVos);
    }

    /**
     * 单条分类详情
     */
    @GetMapping("/detail/{categoryId}")
    public R detailById(@PathVariable("categoryId") Integer categoryId){
        CategoryVo categoryVo = categoryService.queryOneCategoryDetail();
        return R.ok(categoryVo);
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
