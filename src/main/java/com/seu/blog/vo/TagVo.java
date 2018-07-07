package com.seu.blog.vo;

import com.seu.blog.entity.TagEntity;

/**
 * tag详情
 *
 * @author liangfeihu
 * @since 2018/7/7 14:56.
 */
public class TagVo extends TagEntity {

    private static final long serialVersionUID = 5059212992497947120L;

    private int articles;

    public int getArticles() {
        return articles;
    }

    public void setArticles(int articles) {
        this.articles = articles;
    }

}
