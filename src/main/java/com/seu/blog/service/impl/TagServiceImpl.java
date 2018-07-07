package com.seu.blog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.seu.blog.dao.TagDao;
import com.seu.blog.entity.TagEntity;
import com.seu.blog.service.TagService;
import com.seu.blog.vo.TagVo;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 标签表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TagEntity> page = this.selectPage(
                new Query<TagEntity>(params).getPage(),
                new EntityWrapper<TagEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取标签详情
     *
     * @param tagIds
     * @return
     */
    @Override
    public  List<TagEntity> queryHotTagDetails(Integer[] tagIds){
        if (tagIds == null || tagIds.length <= 0){
            return this.selectList(null);
        } else {
            return this.baseMapper.queryHotTagDetails(tagIds);
        }
    }

    /**
     * 查询标签详情
     *
     * @return
     */
    @Override
    public List<TagVo> queryTagDetails() {
        return this.baseMapper.queryTagDetails();
    }

    /**
     * 查询单条标签详情
     *
     * @return
     */
    @Override
    public TagVo queryOneTagDetail() {
        return this.baseMapper.queryOneTagDetail();
    }
}
