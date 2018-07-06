package com.seu.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.code.kaptcha.Producer;
import com.seu.blog.entity.UserEntity;
import com.seu.blog.entity.UserTokenEntity;
import com.seu.blog.form.LoginForm;
import com.seu.blog.service.UserService;
import com.seu.blog.service.impl.CaptchaServiceImpl;
import com.seu.blog.service.impl.UserTokenServiceImpl;
import com.seu.common.component.R;
import com.seu.common.constant.Constant;
import com.seu.common.exception.RRException;
import com.seu.common.utils.ShiroUtils;
import com.seu.shiro.TokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;


/**
 * 登录相关
 *
 * @author liangfeihu
 * @since 2018/7/4 16:32.
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private Producer producer;
    @Resource
    CaptchaServiceImpl captchaService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserTokenServiceImpl userTokenService;

    /**
     * 验证码
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response, String uuid) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        if (StringUtils.isBlank(uuid)) {
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        captchaService.setCaptcha(uuid, code);

        //获取图片验证码
        BufferedImage image = producer.createImage(code);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录2(带验证码)
     */
    @PostMapping("/login2")
    public Map<String, Object> login2(@RequestBody LoginForm form) throws IOException {
        // 获取Session中验证码
        if (StringUtils.isBlank(form.getUuid())){
            return R.error("参数uuid不能为空");
        }
        String randCode = captchaService.getCaptcha(form.getUuid());
        if (StringUtils.isBlank(randCode)){
            return R.error("图片验证码失效，请重新获取");
        }
        // 用户输入的验证码
        String randomCode = form.getCaptcha();
        if (randomCode == null || !randomCode.equalsIgnoreCase(randCode)) {
            return R.error("图片验证码不正确");
        }

        //用户信息
        UserEntity user = userService.queryByUserAccount(form.getUsername());

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if (Constant.UserStatus.PAUSE.getValue().equals(user.getStatus())) {
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        R r = userTokenService.createToken(user.getId());
        return r;
    }

    /**
     * 用户登录(不带验证码)
     *
     * @param json
     * @return
     * @throws IOException
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody JSONObject json) throws IOException {
        String account = json.getString("account");
        String password = json.getString("password");
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return R.error("参数不对");
        }

        //用户信息
        UserEntity user = userService.queryByUserAccount(account);

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if (Constant.UserStatus.PAUSE.getValue().equals(user.getStatus())) {
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        R r = userTokenService.createToken(user.getId());
        return r;
    }

    /**
     * 账号注册
     *
     * @param json
     * @return
     * @throws IOException
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody JSONObject json) throws IOException {
        String account = json.getString("account");
        String password = json.getString("password");
        String nickname = json.getString("nickname");
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            return R.error("参数不对");
        }

        //用户信息
        UserEntity user = userService.queryByUserAccount(account);

        //账号已存在
        if (user != null) {
            return R.error("该账号已存在");
        }

        user = new UserEntity();
        user.setAccount(account);
        user.setAdmin(1);
        user.setCreateTime(new Date());
        user.setNickname(nickname);
        user.setLastLoginTime(new Date());
        user.setStatus(Constant.UserStatus.NORMAL.getValue());
        String salt = RandomStringUtils.randomAlphanumeric(8);
        user.setSalt(salt);
        user.setPassword(new Sha256Hash(password, salt).toHex());
        boolean insert = userService.insert(user);
        if (!insert) {
            return R.error("注册账号失败了");
        }

        //生成token，并保存到数据库
        R r = userTokenService.createToken(user.getId());
        return r;
    }

    /**
     * 退出登录
     *
     * @author liangfeihu
     * @since 2018/7/1 15:30.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public R logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        try {
            Long userId = ShiroUtils.getUserId();
            UserTokenEntity tokenEntity = userTokenService.selectOne(new EntityWrapper<UserTokenEntity>().eq("user_id", userId));
            if (tokenEntity != null){
                String token = TokenGenerator.generateValue();
                tokenEntity.setToken(token);
                tokenEntity.setExpireTime(new Date());
                tokenEntity.setUpdateTime(new Date());
                userTokenService.updateById(tokenEntity);
            }
        } catch (Exception e) {
            log.warn("退出登录, 更新token失败！", e);
        }
        return R.ok();
    }




}
