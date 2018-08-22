package com.seu.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.seu.blog.entity.UserEntity;
import com.seu.blog.service.UserService;
import com.seu.common.component.R;
import com.seu.common.utils.PageUtils;
import com.seu.common.utils.ShiroUtils;
import com.seu.common.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;


/**
 * 用户表
 *
 * @author liangfeihu
 * @email liangfhhd@163.com
 * @date 2018-07-04 15:00:54
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public R getCurrentUser(HttpServletRequest request) {
        UserEntity userEntity = ShiroUtils.getUserEntity();

        JSONObject object = new JSONObject();
        object.put("account", userEntity.getAccount());
        object.put("nickname", userEntity.getNickname());
        object.put("avatar", userEntity.getAvatar());
        object.put("id", userEntity.getId());

        return R.ok(object);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        UserEntity user = userService.selectById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user){
        userService.insert(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user){
        ValidatorUtils.validateEntity(user);
        //全部更新
        userService.updateAllColumnById(user);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        userService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
