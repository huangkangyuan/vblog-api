package com.seu.blog.vo;

import com.seu.blog.entity.CategoryEntity;

/**
 * 文章分类
 *
 * @author liangfeihu
 * @since 2018/7/7 14:57.
 */
public class CategoryVo extends CategoryEntity {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -2975739216517528014L;

    private int articles;

    public int getArticles() {
        return articles;
    }

    public void setArticles(int articles) {
        this.articles = articles;
    }

}
