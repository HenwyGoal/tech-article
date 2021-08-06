# Apollo 安装教程
###### 本系列安装操作手册使用Centos7的Linux虚拟机镜像以及docker拉取的方式

## A. 物料准备
- Linux操作系统
- Docker
---
Centos7安装手册 | Docker安装手册
---|---
[GitHub](https://github.com/HenwyGoal/tech-article/blob/main/install/linux-install.md) | [GitHub](https://github.com/HenwyGoal/tech-article/blob/main/install/docker-install.md)
[有道云笔记](https://note.youdao.com/s/87i4m1Qx) | [有道云笔记](https://note.youdao.com/s/Duzp5eGb)

---
## B. 操作步骤

1. 打开终端+登录超管+启动docker服务
```
su
[输入密码登录root]
systemctl start docker
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/B-01.png)

2. 拉取镜像

> 如果对版本没有执念，则直接拉取最新的版本就行

```
docker pull apolloconfig/apollo-configservice
docker pull apolloconfig/apollo-adminservice
docker pull apolloconfig/apollo-portal
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-01.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-02.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-03.png)

3. 创建挂载目录

> 创建apollo容器的挂载根目录

```
# -p即当上级目录不存在时，会按目录层级自动创建目录
mkdir -p /usr/local/data/hw-apollo   
cd /usr/local/data/hw-apollo
mkdir -p admin/logs config/logs portal/logs
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-04.png)

4. 准备一个mysql数据库

> 此处亦使用docker下载的方式安装MySQL，若已有其他可用数据库，则可继续往下走

MySQL5.7 |
---|
[GitHub](https://github.com/HenwyGoal/tech-article/blob/main/install/mysql-install.md) |
[有道云笔记](https://note.youdao.com/s/GN8xueQ2) |

5. Apollo初始化
> MySQL中初始化数据库

sql初始化脚本 |
---|
[ApolloConfigDB](https://raw.githubusercontent.com/ctripcorp/apollo/master/scripts/docker-quick-start/sql/apolloconfigdb.sql) |
[ApolloPortalDB](https://raw.githubusercontent.com/ctripcorp/apollo/master/scripts/docker-quick-start/sql/apolloportaldb.sql) |

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-05.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-06.png)

6. 启动容器

> 启动这个是有顺序的，所以就按顺序来吧

> 若使用的是已有的mysql链接，则将启动命令的`hw-mysql:3306`替换为mysql的`ip:port`

> 此处使用MySQL的root账号进行连接，如果希望对权限进行控制，则自行新增用户，替换启动命令的账号密码即可

- apollo-configservice
```
docker run -d \
-p 8080:8080 \
--link hw-mysql \
--privileged \
--name hw-apollo-configservice \
-v /usr/local/data/hw-apollo/config/logs:/opt/logs \
-e SPRING_DATASOURCE_URL="jdbc:mysql://hw-mysql:3306/ApolloConfigDB?characterEncoding=utf8&useSSL=false" \
-e SPRING_DATASOURCE_USERNAME=root \
-e SPRING_DATASOURCE_PASSWORD=root \
apolloconfig/apollo-configservice
```
- apollo-adminservice
```
docker run -d \
-p 8090:8090 \
--link hw-mysql \
--link hw-apollo-configservice \
--privileged \
--name hw-apollo-adminservice \
-v /usr/local/data/hw-apollo/admin/logs:/opt/logs \
-e SPRING_DATASOURCE_URL="jdbc:mysql://hw-mysql:3306/ApolloConfigDB?characterEncoding=utf8&useSSL=false" \
-e SPRING_DATASOURCE_USERNAME=root \
-e SPRING_DATASOURCE_PASSWORD=root \
-e eureka_service_url="http://hw-apollo-configservice:8080/eureka" \
apolloconfig/apollo-adminservice
```
- apollo-portal
```
docker run -d \
-p 8070:8070 \
--link hw-mysql \
--link hw-apollo-configservice \
--privileged \
--name hw-apollo-portal \
-v /usr/local/data/hw-apollo/portal/logs:/opt/logs \
-e SPRING_DATASOURCE_URL="jdbc:mysql://hw-mysql:3306/ApolloPortalDB?characterEncoding=utf8&useSSL=false" \
-e SPRING_DATASOURCE_USERNAME=root \
-e SPRING_DATASOURCE_PASSWORD=root \
-e APOLLO_PORTAL_ENVS=dev \
-e DEV_META=http://hw-apollo-configservice:8080 \
apolloconfig/apollo-portal
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-07.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-08.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-09.png)
> 命令解释

key | comment
---|---
--name | 为此容器自定义一个名称
-d | 让此容器在后台运行(守护线程daemon)
-p | 端口映射——linux主机端口:docker容器端口
--privileged | 拥有此容器的root权限
-e | 环境变量设置
\ | 允许命令换行继续编写(用于提高长命令的可读性)
--link | 关联docker内的其他容器服务

5. 打开外网访问(关闭防火墙)

> 打开特定端口

```
firewall-cmd --add-port=8070/tcp
firewall-cmd --add-port=8080/tcp
```
> 关闭防火墙服务

```
systemctl stop firewalld
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-10.png)

> 访问Apollo

```
ip:8070
apollo / admin
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/B-11.png)

## C. 使用Apollo
> 创建配置

```
1. 创建项目
    appId = test-config
2. 在application中新增配置
    test.integer = 1024
3. 发布
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/C-01.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/C-02.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/C-03.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/C-04.png)

> 启动项目

**若无consul用以注册，则去掉`application.yml`中`spring.cloud.consul`的相关配置，以及启动类的`@EnableDiscoveryClient`注解**

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/C-05.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/C-06.png)

**项目配置**
- 修改`application.yml`
```
apollo:
  meta: http://#{linux-ip}:8080
```
- 启动命令增加配置
```
-Dapollo.configService=http://#{linux-ip}:8080
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/C-07.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/C-08.png)

> 启动项目后。如图所示，读取Apollo配置项成功。

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/apollo-install/C-09.png)
