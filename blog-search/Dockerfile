# 基础镜像
FROM openjdk:8-jdk-alpine
  
# 指定工作目录
WORKDIR /app
  
# 将 jar 包添加到工作目录
ADD target/blog-search-0.0.1-SNAPSHOT.jar .
  
# 暴露端口
EXPOSE 8000
  
# 启动命令
ENTRYPOINT ["java","-jar","/app/blog-search-0.0.1-SNAPSHOT.jar",""]