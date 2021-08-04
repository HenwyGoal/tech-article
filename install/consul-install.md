# Consul 安装教程
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
docker pull consul
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/B-02.png)

3. 启动容器
```
docker run --name hw-consul --restart=always -d -p 8500:8500 consul
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/B-03.png)

> 命令解释

key | comment
---|---
--name | 为此容器自定义一个名称
--restart=always | Docker重启时，此容器也重启
-d | 让此容器在后台运行(守护线程daemon)
-p | 端口映射——linux主机端口:docker容器端口

4. 访问consul
- 打开外网访问(关闭防火墙)
> 打开特定端口
```
firewall-cmd --add-port=8500/tcp
```
> 关闭防火墙服务
```
systemctl stop firewalld
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/B-04.png)

- 宿主机访问consul
```
ip:8500
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/B-05.png)

## C. 使用consul
1. 修改配置项
```
spring.cloud.consul.host = #{linux-ip}
```
2. 启动springboot项目

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/C-01.png)

3. 在consul-ui查看项目注册情况
> 若出现service-check失败，ip与真实ip不对应的情况，
请在控制面板中禁用所有VMNet的虚拟机网络适配器

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/C-02.png)
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/consul-install/C-03.png)
