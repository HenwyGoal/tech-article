# MySQL 安装教程
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
docker pull mysql:5.7
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mysql-install/B-02.png)
3. 创建挂载目录

> 创建mysql容器的挂载根目录

```
# -p即当上级目录不存在时，会按目录层级自动创建目录
mkdir -p /usr/local/data/hw-mysql   
```

> 创建具体挂载目录

```
cd /usr/local/data/hw-mysql
mkdir -p logs data conf
```
> 在conf中创建mysql配置文件

```
cd conf
touch my.cnf
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mysql-install/B-03.png)
4. 启动容器

```
docker run --privileged --name hw-mysql -p 3306:3306 \
-v /usr/local/data/hw-mysql/conf:/etc/mysql/conf.d \
-v /usr/local/data/hw-mysql/logs:/logs \
-v /usr/local/data/hw-mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mysql-install/B-04.png)
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
firewall-cmd --add-port=8500/tcp
```
> 关闭防火墙服务

```
systemctl stop firewalld
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/B-04.png)

## C. 使用MySQL
> 本人使用Navicat连接

```
ip:3306
root / root
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mysql-install/C-01.png)
> 创建一个数据库

```
# 创建一个名为test-mysql的数据库
create database `test-mysql`;
# 显示所有数据库
show databases;
```
> 如图所示，数据库创建成功。此次mysql搭建成功结束。

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mysql-install/C-02.png)