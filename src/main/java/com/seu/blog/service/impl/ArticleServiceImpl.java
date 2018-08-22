package com.seu.blog.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.ArticleDao;
import com.seu.blog.entity.*;
import com.seu.blog.service.*;
import com.seu.blog.vo.ArticleArchivesVo;
import com.seu.common.exception.RestException;
import com.seu.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public List<ArticleEntity> queryPage(Map<String, Object> params) {
        EntityWrapper<ArticleEntity> entityWrapper = new EntityWrapper<ArticleEntity>();
        String categoryIdStr = (String) params.get("categoryId");
        if (StringUtils.isNotBlank(categoryIdStr)){
            Integer categoryId = Integer.parseInt(categoryIdStr);
            entityWrapper.eq("category_id", categoryId);
        }

        String year = (String) params.get("year");
        String month = (String) params.get("month");
        if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {
            entityWrapper.eq("year(create_time)", year);
            entityWrapper.eq("month(create_time)", month);
        }


        Page<ArticleEntity> page = this.selectPage(
                new Query<ArticleEntity>(params).getPage(),
                entityWrapper
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
            object.put("tags", article.getTagArray());
            object.put("createTime", article.getCreateTime());
            object.put("viewNum", article.getViewNum());
            object.put("commentNum", article.getCommentNum());

            array.add(object);
        }
        return array;
    }

    /**
     * 根据id获取文章，添加阅读数
     *
     * @param articleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getArticleDetailAndAddViewNum(Long articleId) {
        ArticleEntity article = this.selectById(articleId);
        if (article == null) {
            throw new RestException("该文章不存在");
        }

        JSONObject object = new JSONObject();
        // 1、文章详情
        object.put("id", article.getId());
        object.put("title", article.getTitle());
        object.put("summary", article.getSummary());
        object.put("createTime", article.getCreateTime());

        article.setViewNum(article.getViewNum() + 1);
        object.put("viewNum", article.getViewNum());
        object.put("commentNum", article.getCommentNum());
        object.put("content", article.getContent());

        // 2、文章作者信息
        UserEntity userEntity = userService.selectById(article.getUserId());
        JSONObject user = new JSONObject();
        user.put("id", userEntity.getId());
        user.put("avatar", userEntity.getAvatar());
        user.put("nickname", userEntity.getNickname());
        object.put("author", user);

        // 3、文章所属分类信息
        CategoryEntity categoryEntity = categoryService.selectById(article.getCategoryId());
        object.put("category", categoryEntity);

        // 4、文章所属标签信息
        List<TagEntity> tagEntities = articleTagService.queryArticleTags(article.getId());
        object.put("tags", tagEntities);

        // 5、文章阅读数 + 1
        this.updateById(article);

        return object;
    }

    /**
     * 添加一篇新文章
     *
     * @param userEntity
     * @param json
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addOneArticle(UserEntity userEntity, JSONObject json) {
        Map<Integer, String> maps = getTagMapInfos();

        // 1、保存文章信息
        ArticleEntity article = new ArticleEntity();
        article.setUserId(userEntity.getId());
        article.setNickname(userEntity.getNickname());
        article.setCommentNum(0);
        article.setViewNum(0);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        article.setWeight(0);
        article.setTitle(json.getString("title"));
        article.setSummary(json.getString("summary"));

        JSONObject body = json.getJSONObject("body");
        article.setContent(body.getString("content"));
        article.setContentHtml(body.getString("contentHtml"));

        // 2、设置分类信息
        JSONObject category = json.getJSONObject("category");
        article.setCategoryId(category.getInteger("id"));

        // 3、设置标签信息
        JSONArray tags = json.getJSONArray("tags");
        StringBuilder tagStr = new StringBuilder();
        for (int i = 0; i < tags.size(); i++ ) {
            Integer tagId = tags.getJSONObject(i).getInteger("id");
            if (i != 0){
                tagStr.append(",");
            }
            tagStr.append(maps.get(tagId));
        }
        article.setTags(tagStr.toString());

        // 4、插入文章信息
        this.insert(article);
        Long id = article.getId();

        // 5、插入文章对应的标签信息
        List<ArticleTagEntity> articleTagEntityList = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++ ) {
            Integer tagId = tags.getJSONObject(i).getInteger("id");
            ArticleTagEntity articleTagEntity = new ArticleTagEntity();
            articleTagEntity.setArticleId(id);
            articleTagEntity.setTagId(tagId);
            articleTagEntity.setCreateTime(new Date());
            articleTagEntity.setUpdateTime(new Date());
            articleTagEntityList.add(articleTagEntity);
        }
        articleTagService.insertBatch(articleTagEntityList);

        return id;
    }

    /**
     * 获取标签Map数据
     *
     * @return
     */
    private  Map<Integer, String> getTagMapInfos() {
        List<TagEntity> tagEntities = tagService.selectList(null);
        Map<Integer, String> map = new HashMap<>();
        for (TagEntity tag: tagEntities){
            map.put(tag.getId(), tag.getTagName());
        }
        return map;
    }

    /**
     * 更新一篇文章
     *
     * @param userEntity
     * @param article
     * @param json
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long updateOneArticle(UserEntity userEntity, ArticleEntity article, JSONObject json) {
        Long id = article.getId();
        Map<Integer, String> map = getTagMapInfos();

        // 1、更新文章信息
        article.setTitle(json.getString("title"));
        article.setSummary(json.getString("summary"));

        JSONObject body = json.getJSONObject("body");
        article.setContent(body.getString("content"));
        article.setContentHtml(body.getString("contentHtml"));

        JSONObject category = json.getJSONObject("category");
        if (article.getCategoryId().intValue() != category.getInteger("id").intValue()) {
            article.setCategoryId(category.getInteger("id"));
        }

        // 2、获取文章对应的标签信息
        List<ArticleTagEntity> articleTagEntityList = new ArrayList<>();
        StringBuilder tagStr = new StringBuilder();
        JSONArray tags = json.getJSONArray("tags");
        for (int i = 0; i < tags.size(); i++ ) {
            Integer tagId = tags.getJSONObject(i).getInteger("id");
            if (i != 0){
                tagStr.append(",");
            }
            tagStr.append(map.get(tagId));

            ArticleTagEntity articleTagEntity = new ArticleTagEntity();
            articleTagEntity.setArticleId(id);
            articleTagEntity.setTagId(tagId);
            articleTagEntity.setCreateTime(new Date());
            articleTagEntity.setUpdateTime(new Date());
            articleTagEntityList.add(articleTagEntity);
        }
        article.setTags(tagStr.toString());

        article.setUpdateTime(new Date());
        article.setUserId(userEntity.getId());
        article.setNickname(userEntity.getNickname());
        this.updateById(article);

        // 3、删除之前的文章标签信息
        EntityWrapper<ArticleTagEntity> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("article_id", id);
        articleTagService.delete(entityWrapper);

        // 4、更新文章标签信息
        articleTagService.insertBatch(articleTagEntityList);

        return id;
    }
}
