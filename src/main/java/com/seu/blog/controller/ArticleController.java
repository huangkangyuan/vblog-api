package com.seu.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.seu.blog.entity.*;
import com.seu.blog.service.*;
import com.seu.blog.vo.ArticleArchivesVo;
import com.seu.blog.vo.TagPageVo;
import com.seu.common.component.R;
import com.seu.common.utils.ShiroUtils;
import com.seu.common.validator.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    public static final int HOT_OR_NEW_ARTICLE_NUM = 6;
    public static final int ARTICLE_ARCHIVE_LIMIT_NUM = 8;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表 分页查询
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        String tagIdStr = (String) params.get("tagId");
        if (StringUtils.isNotBlank(tagIdStr)) {
            Integer tagId = Integer.parseInt(tagIdStr);
            TagPageVo tagPageVo = getTagPageVo(params, tagId);
            List<ArticleEntity> articleEntities = articleTagService.queryArticlesByTag(tagPageVo);
            JSONArray array = articleService.getFormatArticleList(articleEntities);
            return R.ok(array);
        }

        List<ArticleEntity> list = articleService.queryPage(params);
        JSONArray array = articleService.getFormatArticleList(list);
        return R.ok(array);
    }

    /**
     * 构造分页参数
     *
     * @param params
     * @param tagId
     * @return
     */
    private TagPageVo getTagPageVo(Map<String, Object> params, Integer tagId){
        Integer pageNo = 1;
        Integer  pageSize = 5;
        //分页参数
        if (params.get("pageNo") != null) {
            pageNo = Integer.parseInt((String) params.get("pageNo"));
        }
        if (params.get("pageSize") != null) {
            pageSize = Integer.parseInt((String) params.get("pageSize"));
        }

        Integer offset =  (pageNo - 1) * pageSize;
        TagPageVo tagPageVo = new TagPageVo(offset, pageSize, tagId);
        return tagPageVo;
    }

    /**
     * 最热文章
     */
    @GetMapping("/hot")
    public R listHotArticles() {
        JSONArray array = getHotOrNewArticles("view_num");
        return R.ok(array);
    }

    /**
     * 最新文章
     */
    @GetMapping("/new")
    public R listNewArticles() {
        JSONArray array = getHotOrNewArticles("create_time");
        return R.ok(array);
    }

    /**
     * 获取最热或最新文章
     * type 可取 view_num 最热
     *        create_time 最新
     *
     * @param type
     * @return
     */
    private JSONArray getHotOrNewArticles(String type) {
        Page<ArticleEntity> page = new Page<>(1, HOT_OR_NEW_ARTICLE_NUM);
        page.setOrderByField(type);
        page.setAsc(false);

        Page<ArticleEntity> pageList = articleService.selectPage(page,new EntityWrapper<ArticleEntity>());

        JSONArray array = new JSONArray();
        for (ArticleEntity article : pageList.getRecords()) {
            JSONObject object = new JSONObject();
            object.put("id", article.getId());
            object.put("title", article.getTitle());
            array.add(object);
        }
        return array;
    }

    /**
     * 汇总查询
     */
    @GetMapping("/archives")
    public R archives(){
        List<ArticleArchivesVo> archivesVos = articleService.queyArticleArchives(ARTICLE_ARCHIVE_LIMIT_NUM);
        return R.ok(archivesVos);
    }

    /**
     * 查看文章详情时：
     *  获取文章详情
     *  包含作者信息
     */
    @GetMapping("/view/{id}")
    public R oneArticleInfo(@PathVariable("id") Long id){
        ArticleEntity article = articleService.selectById(id);
        JSONObject object = new JSONObject();
        object.put("id", article.getId());
        object.put("title", article.getTitle());
        object.put("summary", article.getSummary());
        object.put("createTime", article.getCreateTime());
        object.put("viewNum", article.getViewNum());
        object.put("commentNum", article.getCommentNum());
        object.put("content", article.getContent());

        UserEntity userEntity = userService.selectById(article.getUserId());
        JSONObject user = new JSONObject();
        user.put("id", userEntity.getId());
        user.put("avatar", userEntity.getAvatar());
        user.put("nickname", userEntity.getNickname());

        object.put("author", user);

        CategoryEntity categoryEntity = categoryService.selectById(article.getCategoryId());
        object.put("category", categoryEntity);

        List<TagEntity> tagEntities = articleTagService.queryArticleTags(article.getId());
        object.put("tags", tagEntities);

        return R.ok(object);
    }


    /**
     * 编辑文章时：
     *  通过文章Id获取文章详情
     *  不需要用户信息
     */
    @GetMapping("/{id}")
    public R getArticleById(@PathVariable("id") Long id){
        ArticleEntity article = articleService.selectById(id);
        JSONObject object = new JSONObject();
        object.put("id", article.getId());
        object.put("title", article.getTitle());
        object.put("summary", article.getSummary());
        object.put("content", article.getContent());

        CategoryEntity categoryEntity = categoryService.selectById(article.getCategoryId());
        object.put("category", categoryEntity);

        List<TagEntity> tagEntities = articleTagService.queryArticleTags(article.getId());
        object.put("tags", tagEntities);

        return R.ok(object);
    }

    /**
     * 保存
     */
    @PostMapping("/publish")
    public R save(@RequestBody JSONObject json){
        UserEntity userEntity = ShiroUtils.getUserEntity();
        Long id = json.getLong("id");
        JSONObject category = json.getJSONObject("category");
        JSONArray tags = json.getJSONArray("tags");
        List<TagEntity> tagEntities = tagService.selectList(null);
        Map<Integer, String> map = new HashMap<>();
        for (TagEntity tag: tagEntities){
            map.put(tag.getId(), tag.getTagName());
        }
        StringBuilder tagStr = new StringBuilder();
        for (int i = 0; i < tags.size(); i++ ) {
            Integer tagId = tags.getJSONObject(i).getInteger("id");
            if (i != 0){
                tagStr.append(",");
            }
            tagStr.append(map.get(tagId));
        }
        log.info("publish tags={}", tagStr.toString());
        if (id != null){
            //修改
            ArticleEntity article = articleService.selectById(id);
            article.setTitle(json.getString("title"));
            article.setSummary(json.getString("summary"));
            JSONObject body = json.getJSONObject("body");
            article.setContent(body.getString("content"));
            article.setContentHtml(body.getString("contentHtml"));

            if (article.getCategoryId().intValue() != category.getInteger("id").intValue()) {
                article.setCategoryId(category.getInteger("id"));
            }

            article.setTags(tagStr.toString());
            EntityWrapper<ArticleTagEntity> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("article_id", id);
            articleTagService.delete(entityWrapper);

            article.setUpdateTime(new Date());
            article.setUserId(userEntity.getId());
            article.setNickname(userEntity.getNickname());
            articleService.updateById(article);

        } else {
            //保存
            ArticleEntity article = articleService.selectById(id);
            article.setTitle(json.getString("title"));
            article.setSummary(json.getString("summary"));
            JSONObject body = json.getJSONObject("body");
            article.setContent(body.getString("content"));
            article.setContentHtml(body.getString("contentHtml"));
            article.setCategoryId(category.getInteger("id"));
            article.setUserId(userEntity.getId());
            article.setNickname(userEntity.getNickname());
            article.setCommentNum(0);
            article.setViewNum(0);
            article.setCreateTime(new Date());
            article.setUpdateTime(new Date());
            article.setWeight(0);
            article.setTags(tagStr.toString());
            articleService.insert(article);
            id = article.getId();
        }

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

        JSONObject object = new JSONObject();
        object.put("articleId", id);
        return R.ok(object);
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
