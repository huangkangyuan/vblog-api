package com.seu.blog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.CategoryDao;
import com.seu.blog.entity.CategoryEntity;
import com.seu.blog.service.CategoryService;
import com.seu.blog.vo.CategoryVo;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 文章类别表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CategoryEntity> page = this.selectPage(
                new Query<CategoryEntity>(params).getPage(),
                new EntityWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 文章分类详情
     * @return
     */
    @Override
    public List<CategoryVo> queryCategoryDetails() {
        return this.baseMapper.queryCategoryDetails();
    }

    /**
     * 单条文章分类详情
     *
     * @return
     */
    @Override
    public CategoryVo queryOneCategoryDetail(Integer categoryId) {
        return this.baseMapper.queryOneCategoryDetail(categoryId);
    }
}
