# blog

#### 介绍
个人学习技术分布式博客后端项目

#### 软件架构
- blog-auth：公共认证服务
- blog-common：公共依赖服务
- blog-gateway：API网关服务
- blog-message：消息服务
- blog-picture：图片服务
- blog-search：检索服务
- blog-web：Web端api服务


#### 安装教程

1. 下载本项目
2. 需要安装的软件或中间件：Mysql v8.0+、Redis、Nacos、ElasticSearch（检索服务使用）、RabbitMQ（消息服务使用） 
3. 修改连接地址等配置，启动各模块即可。