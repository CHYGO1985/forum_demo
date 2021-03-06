# 论坛
此论坛为学习Java web开发而做的项目。项目所用到的技术组合有：SpringBoot, Freemarker, Mybatis, MySQL and Redis. 项目为学习之用，所以不定期添加各种功能。
目前已实现的功能有：

* [注册/登录] - 登录/注册功能，并使用往cookie中添加ticket的做法以及拦截器实现类似SSO的功能
* [提交问题] - 用户往首页添加问题，并实现关键词过滤功能 
* [提交评论] - 用户评论问题或者对评论发表评论，并实现关键词过滤功能
* [点赞/取消赞] - 用户可以对评论点赞或者取消，被点赞的用户会收到通知
* [关注/取消关注] - 关注/取消关注用户或者问题 
* [私信] - 给其他用户发送私人信息，系统显示私信列表
* [异步事件处理] - 异步处理非紧急事件

以下为各个模块所使用实现技术的简介：
### 注册/登录
  - 密码采用加salt的MD5加密方式
  - 拦截器以及ticket的使用。用户登录后会往cookie中添加ticket。以后用户每访问一个新的页面，拦截器（利用AOP的概念）都会根据cookie中的ticket来决定该用户是否处于已
登录状态，由此实现类似SSO的动能
  - 实现注册/登录后返回之前访问的页面

### 提交问题
使用传统的关系型数据库(MySQL)存储问题数据。问题的题目和内容会经过关键词过滤。关键词过滤使用<ins>前缀树(TriNode)</ins>实现。首先通过关键词字典中词语列表构建
关键词前缀树，然后用前缀树来过滤问题标题和内容所包含的关键词。

### 点赞/取消赞
这一模块使用redis中的set来存储。key的构建利用了用户ID和被赞的实体ID。利用set不允许相同元素存在的特点避免了同一用户重复点赞。取消赞则从set中删除相应的key即可。

### 关注/取消关注
使用了redis中的sorted set来存储。使用sort set的原因是可以根据关注的时间这个score来对关注列表进行排序。

### 异步事件处理
基于生产者&消费者模式以及多线程来实现异步事件处理。事件队列使用redis中的list来实现。事件生产者往事件列队中添加事件，事件消费者从队列中取出事件，交由相应的
事件Handler异步处理。异步事件的例子有：用户发表的评论被点赞后会收到一条通知邮件或者用户被关注后会收到一条通知邮件。

