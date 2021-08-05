# PostgreSQL 安装教程
###### 本系列安装操作手册使用Centos7的Linux虚拟机镜像以及docker拉取的方式

## A. 物料准备
- Linux操作系统
- Docker
---
Centos7安装手册 | Docker安装手册
---|---
[GitHub](https://github.com/HenwyGoal/tech-article/blob/main/install/linux-install.md) | [GitHub](https://github.com/HenwyGoal/tech-article/blob/main/install/docker-install.md)
[网易云笔记](https://note.youdao.com/s/87i4m1Qx) | [网易云笔记](https://note.youdao.com/s/Duzp5eGb)

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
docker pull postgres:latest
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/postgres-install/B-01.png)
3. 创建挂载目录

> 创建mysql容器的挂载根目录

```
# -p即当上级目录不存在时，会按目录层级自动创建目录
mkdir -p /usr/local/data/hw-postgres/data 
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/postgres-install/B-02.png)
4. 启动容器

```
docker run --name hw-postgres \
--privileged \
-e POSTGRES_USER="postgres" \
-e POSTGRES_DB="postgres" \
-e POSTGRES_PASSWORD="postgres"  \
-v /usr/local/data/hw-postgres/data:/var/lib/postgresql/data \
-p 5432:5432 \
-d postgres
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/postgres-install/B-03.png)
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
firewall-cmd --add-port=5432/tcp
```
> 关闭防火墙服务

```
systemctl stop firewalld
service  iptables stop
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/B-04.png)

## C. 使用PostgreSQL
> 本人使用Navicat连接

```
ip:5432
postgres
postgres / postgres
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/postgres-install/C-01.png)

**虽然Navicat能够连接PG，但我并不会使用，所以以另一种方式验证此容器的可用性**

> 进入容器中登录Postgres

```
docker exec -it -u postgres hw-postgres  /bin/bash
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/postgres-install/C-02.png)

> 进入PostgreSQl命令行

```
psql
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/postgres-install/C-03.png)
> 查询所有数据库

```
\l
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/postgres-install/C-04.png)

> 如图所示，数据库查询成功。此次PostgreSQL搭建成功结束。  
> 使用`\q`命令退出SQL命令行  
> 使用`exit`命令退出容器 