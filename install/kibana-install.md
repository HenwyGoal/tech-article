# Kibana 安装教程
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
> 一般建议kibana版本与elasticsearch版本相同

```
docker pull kibana:7.5.1
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/kibana-install/B-01.png)

3. 需要提供一个Elasticsearch

Elasticsearch安装手册 | 
---|
[GitHub](https://note.youdao.com/) | 
[有道云笔记](https://note.youdao.com/) | 

4. 启动容器

> 若有其他es源，则将hw-elasticsearch:9200替换为ip:port

```
docker run --name hw-kibana \
--privileged \
--link hw-elasticsearch \
-e ELASTICSEARCH_HOSTS=http://hw-elasticsearch:9200 \
-p 5601:5601 -d kibana:7.5.1
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/kibana-install/B-02.png)
> 命令解释

key | comment
---|---
--name | 为此容器自定义一个名称
-d | 让此容器在后台运行(守护线程daemon)
-p | 端口映射——linux主机端口:docker容器端口
--privileged | 拥有此容器的root权限
-e | 环境变量设置
\ | 允许命令换行继续编写(用于提高长命令的可读性)
--link | 关联其他容器

5. 打开外网访问(关闭防火墙)

> 打开特定端口

```
firewall-cmd --add-port=5601/tcp
```
> 或 关闭防火墙服务

```
systemctl stop firewalld
service  iptables stop
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/kibana-install/B-03.png)

## C. 使用Kibana

> 访问管理页面

```
ip:5601
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/kibana-install/C-01.png)

> 访问开发者工具

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/kibana-install/C-02.png)

> 直接运行默认给出的请求

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/kibana-install/C-03.png)


> 查看所有索引

```
GET /_cat/indices?v
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/kibana-install/C-04.png)
