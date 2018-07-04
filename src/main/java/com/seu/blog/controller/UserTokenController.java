package com.seu.blog.controller;

import com.seu.blog.entity.UserTokenEntity;
import com.seu.blog.service.UserTokenService;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;
import com.seu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 系统用户Token
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@RestController
@RequestMapping("blog/usertoken")
public class UserTokenController {
    @Autowired
    private UserTokenService userTokenService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("blog:usertoken:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userTokenService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("blog:usertoken:info")
    public R info(@PathVariable("id") Long id){
        UserTokenEntity userToken = userTokenService.selectById(id);

        return R.ok().put("userToken", userToken);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("blog:usertoken:save")
    public R save(@RequestBody UserTokenEntity userToken){
        userTokenService.insert(userToken);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("blog:usertoken:update")
    public R update(@RequestBody UserTokenEntity userToken){
        ValidatorUtils.validateEntity(userToken);
        //全部更新
        userTokenService.updateAllColumnById(userToken);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("blog:usertoken:delete")
    public R delete(@RequestBody Long[] ids){
        userTokenService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
