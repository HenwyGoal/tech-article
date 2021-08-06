# Redis 安装教程
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
docker pull redis
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/redis-install/B-01.png)
3. 创建挂载目录

> 创建mysql容器的挂载根目录

```
# -p即当上级目录不存在时，会按目录层级自动创建目录
mkdir -p /usr/local/data/hw-redis/data
cd /usr/local/data/hw-redis
touch redis.conf
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/redis-install/B-02.png)
4. 启动容器

```
docker run -p 6379:6379 --name hw-redis \
--privileged \
-v /usr/local/data/hw-redis/redis.conf:/etc/redis/redis.conf \
-v /usr/local/data/hw-redis/data:/data \
-d redis redis-server /etc/redis/redis.conf
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/redis-install/B-03.png)
> 命令解释

key | comment
---|---
--name | 为此容器自定义一个名称
-d | 让此容器在后台运行(守护线程daemon)
-p | 端口映射——linux主机端口:docker容器端口
--privileged | 拥有此容器的root权限
-v | 目录挂载——linux主机目录:docker容器目录
-e | 环境变量设置
\ | 允许命令换行继续编写(用于提高长命令的可读性)

5. 打开外网访问(关闭防火墙)

> 打开特定端口

```
firewall-cmd --add-port=6379/tcp
```
> 或 关闭防火墙服务

```
systemctl stop firewalld
service  iptables stop
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/redis-install/B-04.png)

## C. 使用Redis
> 本人使用RedisDesktop连接

```
ip:6379
无密码
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/redis-install/C-01.png)

> 测试redis可用性

```
set key1 value1
get key1
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/redis-install/C-02.png)

> 如图所示，Redis功能正常，此次安装验证完毕。  