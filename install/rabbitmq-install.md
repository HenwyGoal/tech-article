# RabbitMQ 安装教程
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
docker pull rabbitmq:management
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/rabbitmq-install/B-01.png)

3. 启动容器

```
docker run -d --name hw-rabbitmq \
--privileged \
-p 5672:5672 \
-p 15672:15672 \
rabbitmq:management
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/rabbitmq-install/B-02.png)
> 命令解释

key | comment
---|---
--name | 为此容器自定义一个名称
-d | 让此容器在后台运行(守护线程daemon)
-p | 端口映射——linux主机端口:docker容器端口
--privileged | 拥有此容器的root权限
-e | 环境变量设置
\ | 允许命令换行继续编写(用于提高长命令的可读性)

5. 打开外网访问(关闭防火墙)

> 打开特定端口

```
firewall-cmd --add-port=5672/tcp
firewall-cmd --add-port=15672/tcp
```
> 或 关闭防火墙服务

```
systemctl stop firewalld
service  iptables stop
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/rabbitmq-install/B-03.png)

## C. 使用RabbitMQ

> 访问管理页面

```
ip:15672
guest / guest
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/rabbitmq-install/C-01.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/rabbitmq-install/C-02.png)
