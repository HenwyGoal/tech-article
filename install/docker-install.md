# Docker 安装教程
###### 本系列安装操作手册使用Centos7的Linux虚拟机镜像

## A. 物料准备
- Linux操作系统
---
Centos7安装手册 |
:--:|
[GitHub](https://github.com/HenwyGoal/tech-article/blob/main/install/linux-install.md) |
[网易云笔记](https://note.youdao.com/s/87i4m1Qx) |
---
## B. 操作步骤
1. 打开终端，登录超级管理员root
```
su / sudo
```
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/docker-install/B-01.png)

2. Centos使用yum命令下载docker
- 查看是否已使用yum下载过docker
```
yum list installed | grep docker
```
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/docker-install/B-02.png)
- 下载docker
```
yum install -y docker
```
> -y 即下载过程中的选择都为yes

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/docker-install/B-03.png)
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/docker-install/B-04.png)
3. 启动docker
```
systemctl start docker
```
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/docker-install/B-05.png)
4. 查看docker是否运行
```
systemctl status docker
```
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/docker-install/B-06.png)
5. 终止docker
```
systemctl stop docker
```
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/docker-install/B-07.png)
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/docker-install/B-08.png)

## C. 常用命令

命令 | 功能
---|---
`docker pull ${imageName}:${imageVersion}` | 拉取镜像[若不指定版本，则默认拉取最新镜像]
`docker start ${containerName}` | 启动容器
`docker stop ${containerName}` | 关闭容器
`docker rm ${containerId}` | 移除容器
`docker rmi ${imageId}` | 移除镜像
`docker logs ${containerName}` | 查看容器启动日志
`docker ps` | 查看正在运行中的容器
`docker ps -a` | 查看所有已定义的容器
`docker ps | grep ${keyword}` | 关键字查询正在运行中的容器
`docker ps -a | grep ${keyword}` | 关键字查询所有已定义的容器

