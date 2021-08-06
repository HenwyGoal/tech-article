# Sonarqube 安装教程
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
docker pull sonarqube:latest
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-01.png)

3. 创建挂载目录

> 创建sonarqube容器的挂载根目录

```
# -p即当上级目录不存在时，会按目录层级自动创建目录
mkdir -p /usr/local/data/hw-sonarqube   
cd /usr/local/data/hw-sonarqube
mkdir -p data extensions logs temp
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-02.png)

4. 修改虚拟机设置

```
# 修改配置
vim /etc/sysctl.conf
# 新增此配置
vm.max_map_count=262144
# 修改立即生效
/sbin/sysctl -p
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-03.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-04.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-05.png)

5. 准备一个PostgreSQL数据库

> 此处亦使用docker下载的方式安装PG，若已有其他可用数据库，则可继续往下走

PostgreSQL |
---|
[GitHub](https://github.com/HenwyGoal/tech-article/blob/main/install/postgres-install.md) |
[有道云笔记](https://note.youdao.com/s/Fo8mrcEg) |

> 初始化PG中关于Sonarqube的内容

```
DROP DATABASE IF EXISTS sonarqube;
CREATE DATABASE sonarqube WITH  ENCODING = 'UTF8';
GRANT ALL PRIVILEGES ON DATABASE sonarqube TO postgres;
GRANT ALL PRIVILEGES ON all tables in schema public TO postgres;
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-06.png)

6. 启动容器

> 若使用的是已有的postgres链接，则将启动命令的`hw-postgres:5432`替换为mysql的`ip:port`

> 此处使用PostgreSQL的postgres账号进行连接，如果希望对权限进行控制，则自行新增用户，替换启动命令的账号密码即可

```
docker run -d --privileged --name hw-sonarqube -p 9000:9000 \
--link hw-postgres \
-v /usr/local/data/hw-sonarqube/data:/opt/sonarqube/data \
-v /usr/local/data/hw-sonarqube/extensions:/opt/sonarqube/extensions \
-v /usr/local/data/hw-sonarqube/logs:/opt/sonarqube/logs \
-v /usr/local/data/hw-sonarqube/temp:/opt/sonarqube/temp \
-e SONAR_JDBC_URL="jdbc:postgresql://hw-postgres:5432/sonarqube?useUnicode=true&characterEncoding=utf-8" \
-e SONAR_JDBC_USERNAME="postgres" \
-e SONAR_JDBC_PASSWORD="postgres" \
-e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true \
sonarqube:latest
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-07.png)

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
firewall-cmd --add-port=9000/tcp
```
> 或 关闭防火墙服务

```
systemctl stop firewalld
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-08.png)

> 访问Sonarqube

```
ip:9000
admin / admin
设置admin新密码为sonarqube
```

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-09.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-10.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/B-11.png)

## C. 使用Sonarqube
> 创建项目

1. 我是本地工程，使用manually手动方式创建

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/C-01.png)
2. 给项目命名test-project

   > **(一般跟spring.application.name一样就好了，比较好辨认)**

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/C-02.png)
3. 选择Locally本地工程

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/C-03.png)
4. 生成token校验令牌

   > **token-key也使用test-project，都是为了好记**

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/C-04.png)
5. 选择项目类型，复制分析命令

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/C-05.png)
6. 在本地项目中执行命令

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/C-06.png)
7. 分析成功后，浏览器页面自动更新。本次安装验证成功。

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/C-07.png)

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/sonarqube-install/C-08.png)

> 若代码分析失败，且错误信息如下

```
[ERROR] Failed to execute goal org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar (default-cli) on project test-project: Execution default-cli of goal org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar failed: An API incompatibility was encountered while executing org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar: java.lang.UnsupportedClassVersionError: org/sonar/batch/bootstrapper/EnvironmentInformation has been compiled by a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only recognizes class file versions up to 52.0
```
> 则此为jdk版本问题，需要将jdk版本提高 ==(我就是jdk8报的错，改成jdk15就成功通过了)==
