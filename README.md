# vblog-api

项目预览地址： https://blog.xiaoxinfq.com/

#### 项目介绍
VBlog 是一款基于最新技术开发的多人在线、简洁的博客系统。

vblog-api是该博客系统的后端API接口服务；

该博客系统的前端页面代码见https://gitee.com/seu-lfh/vblog-web.git

#### 软件架构
采用SpringBoot、MyBatis-Plus、Quartz等框架

**具有如下特点**
- 友好的代码结构及注释，便于阅读及二次开发
- 实现前后端分离，通过token进行数据交互，前端再也不用关注后端技术
- 页面交互使用Vue2.x，极大的提高了开发效率
- 引入quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复及日志查看等功能
- 引入Hibernate Validator校验框架，轻松实现后端校验
- 引入swagger文档支持，方便编写API接口文档
<br>


#### 使用说明

1. 在application.yml配置数据库连接信息；
2. 启动该springboot项目；



#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

