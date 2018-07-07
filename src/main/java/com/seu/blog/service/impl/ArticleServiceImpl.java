package com.seu.blog.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.ArticleDao;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.service.ArticleService;
import com.seu.blog.vo.ArticleArchivesVo;
import com.seu.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Override
    public List<ArticleEntity> queryPage(Map<String, Object> params) {
        Page<ArticleEntity> page = this.selectPage(
                new Query<ArticleEntity>(params).getPage(),
                new EntityWrapper<ArticleEntity>()
        );

        return page.getRecords();
    }

    /**
     * 发布文章按年-月汇总
     *
     * @param limit
     * @return
     */
    @Override
    public List<ArticleArchivesVo> queyArticleArchives(int limit) {
        return this.baseMapper.queyArticleArchives(limit);
    }

    /**
     * 格式化文章列表输出
     *
     * @param list
     * @return
     */
    @Override
    public JSONArray getFormatArticleList(List<ArticleEntity> list) {
        JSONArray array = new JSONArray();
        for (ArticleEntity article :list) {
            JSONObject object = new JSONObject();
            object.put("id", article.getId());
            object.put("nickname", article.getNickname());
            object.put("title", article.getTitle());
            object.put("summary", article.getSummary());
            object.put("weight", article.getWeight());
            object.put("tags", article.getTags());
            object.put("createTime", article.getCreateTime());
            object.put("viewNum", article.getViewNum());
            object.put("commentNum", article.getCommentNum());

            array.add(object);
        }
        return array;
    }

}
