# SQL-SERVER 安装教程
###### 本系列安装操作手册使用Centos7的Linux虚拟机镜像以及docker拉取的方式

## A. 物料准备
- Linux操作系统
- Docker
---
Centos7安装手册 | Docker安装手册
:--:|:--:
[GitHub](https://github.com/HenwyGoal/tech-article/blob/main/install/linux-install.md) | [GitHub](https://github.com/HenwyGoal/tech-article/blob/main/install/docker-install.md)
[网易云笔记](https://note.youdao.com/s/87i4m1Qx) | [网易云笔记](https://note.youdao.com/s/Duzp5eGb)

---
## B. 操作步骤

> 本文所有内容取自微软官方文档，仅作一些必要步骤的列举。有兴趣的可以访问[此文来源链接](https://docs.microsoft.com/en-us/sql/linux/quickstart-install-connect-docker?view=sql-server-ver15&pivots=cs1-bash#pullandrun2019)

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
docker pull mcr.microsoft.com/mssql/server:2019-latest
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mssql-install/B-01.png)
3. 启动容器

```
docker run --name hw-sql-server \
--privileged \
-e "ACCEPT_EULA=Y" \
-e "SA_PASSWORD=root@1234" \
-p 1433:1433 \
-d \
mcr.microsoft.com/mssql/server:2019-latest
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mssql-install/B-02.png)
> 命令解释

key | comment
---|---
--name | 为此容器自定义一个名称
-d | 让此容器在后台运行(守护线程daemon)
-p | 端口映射——linux主机端口:docker容器端口
--privileged | 拥有此容器的root权限
-e | 环境变量设置
\ | 允许命令换行继续编写(用于提高长命令的可读性)

> 命令关键参数解释(翻译自[官网链接](https://docs.microsoft.com/en-us/sql/linux/quickstart-install-connect-docker?view=sql-server-ver15&pivots=cs1-bash#pullandrun2019))

param | description
---|---
-e "ACCEPT_EULA=Y" | 将ACCEPT_EULA变量设置为任意值，以确认您接受最终用户许可协议。SQL Server映像的必需设置
-e "SA_PASSWORD=<YourStrong@Passw0rd>" | 指定您自己的强密码，该密码至少为8个字符，并满足SQL Server密码要求。SQL Server映像的必需设置。

> [SQL-SERVER密码要求](https://docs.microsoft.com/en-us/sql/relational-databases/security/password-policy?view=sql-server-ver15)

- 密码不包含用户的账户名。
- 密码长度至少为8个字符
- 密码包含以下四个类别中的三个字符
    - 拉丁文大写字母（A到Z）
    - 拉丁文小写字母（a到z）
    - 以10为基数的数字（0到9）
    - 非字母数字字符，如 **! / $ / # / % / @**
- 密码最长可达128个字符。使用尽可能长和复杂的密码

> [强密码详解](https://docs.microsoft.com/en-us/sql/relational-databases/security/strong-passwords?view=sql-server-ver15)

5. 打开外网访问(关闭防火墙)

> 打开特定端口

```
firewall-cmd --add-port=1433/tcp
```
> 关闭防火墙服务

```
systemctl stop firewalld
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mssql-install/B-03.png)

## C. 使用SQL-SERVER
> 本人使用Navicat连接

```
ip:1433
master
sa / root@1234
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mssql-install/C-01.png)
> 创建一个数据库

```
# 创建一个名为test-mysql的数据库
create database TestDb
# 显示所有数据库
select Name from sys.Databases
# 执行上面的2个命令
go
```
> 如图所示，数据库创建成功。此次mysql搭建成功结束。

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/mssql-install/C-02.png)