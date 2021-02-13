# 聚赛堂（后端）

针对大学生竞赛组队困难、沟通不便等痛点研发的系统。系统包含发布比赛、组队报名、私聊等功能。

**************************

**项目源码使用Scala编写，Java版请详见：<https://github.com/Tracyyy-s/jusaitang-back>**  
**************************


**技术栈：**

- MyBatis + Redis

    使用MyBatis+Redis操作数据库并缓存关键数据，提高系统性能。

- Shiro + JWT
  
    项目使用shiro + JWT进行授权认证，以token作为令牌实现前后分离。


- WebSocket （开发中）

    项目实现基于websocket的私聊功能，聊天内容支持文字与图片。


