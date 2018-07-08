package com.seu.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.seu.blog.entity.ArticleEntity;
import com.seu.blog.entity.CategoryEntity;
import com.seu.blog.entity.TagEntity;
import com.seu.blog.entity.UserEntity;
import com.seu.blog.service.ArticleService;
import com.seu.blog.service.ArticleTagService;
import com.seu.blog.service.CategoryService;
import com.seu.blog.service.UserService;
import com.seu.blog.vo.ArticleArchivesVo;
import com.seu.blog.vo.TagPageVo;
import com.seu.common.component.R;
import com.seu.common.validator.ValidatorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 文章表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:55
 */
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
     * 获取文章详情
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
