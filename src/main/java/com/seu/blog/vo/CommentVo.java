package com.seu.blog.vo;

import com.seu.blog.entity.CommentEntity;

/**
 * Created by liangfeihu on 2018/7/10.
 */
public class CommentVo extends CommentEntity {

    private String avatar;
    private String nickname;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
