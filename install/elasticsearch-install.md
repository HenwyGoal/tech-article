# ElasticSearch 安装教程
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
docker pull elasticsearch:7.5.1
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/elasticsearch-install/B-01.png)

3. 创建挂载目录

> 创建挂载目录与配置文件

```
# -p即当上级目录不存在时，会按目录层级自动创建目录
mkdir -p /usr/local/data/hw-elasticsearch 
cd /usr/local/data/hw-elasticsearch 
mkdir -p config data plugins
chmod 777 data
cd config
touch elasticsearch.yml
```

> 编辑配置文件

```
vim elasticsearch.yml
http.host: 0.0.0.0
network.host: 127.0.0.1
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/elasticsearch-install/B-02.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/elasticsearch-install/B-03.png)

4. 启动容器

```
docker run --name hw-elasticsearch -p 9200:9200 -p 9300:9300  \
--privileged \
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms1024m -Xmx1024m" \
-v /usr/local/data/hw-elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /usr/local/data/hw-elasticsearch/data:/usr/share/elasticsearch/data \
-v /usr/local/data/hw-elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.5.1
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/elasticsearch-install/B-04.png)
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
firewall-cmd --add-port=9200/tcp
```
> 或 关闭防火墙服务

```
systemctl stop firewalld
service  iptables stop
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/elasticsearch-install/B-05.png)

## C. 使用Elasticsearch

> 访问管理页面

```
ip:9200
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/elasticsearch-install/C-01.png)

> 查看所有索引

```
http://ip:9200/_cat/indices?v
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/elasticsearch-install/C-02.png)
