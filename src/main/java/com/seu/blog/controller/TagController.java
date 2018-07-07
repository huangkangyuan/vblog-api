package com.seu.blog.controller;

import com.seu.blog.entity.TagEntity;
import com.seu.blog.service.ArticleTagService;
import com.seu.blog.service.TagService;
import com.seu.blog.vo.TagVo;
import com.seu.common.component.R;
import com.seu.common.validator.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Slf4j
@RestController
@RequestMapping("/tags")
public class TagController {
    public static final int HOT_ARTICLE_TAG_NUM = 4;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        List<TagEntity> tagEntities = tagService.selectList(null);
        return R.ok(tagEntities);
    }

    /**
     * 热门
     */
    @GetMapping("/hot")
    public R hot(){
        List<Integer> tagIds = articleTagService.queryHotTagIds(HOT_ARTICLE_TAG_NUM);
        log.info("tagIds.size()={} {}", tagIds.size(), tagIds.toString());
        Integer[] tagIdsArray = new Integer[tagIds.size()];
        /*for (int i = 0; i < tagIds.size(); i++) {
            tagIdsArray[i] = tagIds.get(i);
        }*/
        List<TagEntity> tagEntities = tagService.queryHotTagDetails(tagIds.toArray(tagIdsArray));
        return R.ok(tagEntities);
    }

    /**
     * 查询标签详情
     */
    @GetMapping("/detail")
    public R detail(){
        List<TagVo> tagVos = tagService.queryTagDetails();
        return R.ok(tagVos);
    }

    /**
     * 单条标签详情
     */
    @GetMapping("/detail/{tagId}")
    public R detailById(@PathVariable("tagId") Integer tagId){
        TagVo tagVo = tagService.queryOneTagDetail();
        return R.ok(tagVo);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("blog:tag:save")
    public R save(@RequestBody TagEntity tag){
        tagService.insert(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("blog:tag:update")
    public R update(@RequestBody TagEntity tag){
        ValidatorUtils.validateEntity(tag);
        //全部更新
        tagService.updateAllColumnById(tag);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("blog:tag:delete")
    public R delete(@RequestBody Integer[] ids){
        tagService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
