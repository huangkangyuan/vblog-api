package com.seu.blog.controller;

import com.seu.blog.entity.LogEntity;
import com.seu.blog.service.LogService;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;
import com.seu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 用户操作日志表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@RestController
@RequestMapping("blog/log")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("blog:log:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("blog:log:info")
    public R info(@PathVariable("id") Long id){
        LogEntity log = logService.selectById(id);

        return R.ok().put("log", log);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("blog:log:save")
    public R save(@RequestBody LogEntity log){
        logService.insert(log);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("blog:log:update")
    public R update(@RequestBody LogEntity log){
        ValidatorUtils.validateEntity(log);
        //全部更新
        logService.updateAllColumnById(log);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("blog:log:delete")
    public R delete(@RequestBody Long[] ids){
        logService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
