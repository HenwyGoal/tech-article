# Linux 安装教程
###### 本安装操作手册使用Centos7的Linux虚拟机镜像
## 物料准备
- 操作系统(Win10)  
  **我的内存是自己买的，不用那么刻意。内存这东西有个8G基本能凑合了**

项目 | 详情
---|---
处理器 | Intel(R) Core(TM) i7-9750H CPU @ 2.60GHz   2.59 GHz
机带 RAM | 32.0 GB
系统类型 | 64 位操作系统, 基于 x64 的处理器

- 虚拟机镜像  
  **此处直接使用清华提供的Centos7镜像。更多镜像请直接访问[https://www.centos.org/download/](https://www.centos.org/download/)**  
  [Centos7清华镜像下载地址](https://mirrors.tuna.tsinghua.edu.cn/centos/7.9.2009/isos/x86_64/CentOS-7-x86_64-DVD-2009.iso)
- 虚拟机管理器  
  **此处使用Vmware Workstation Player，不为别的，就是小且够用而且必然免费。Pro是1个月试用的，破解密钥能找到那也是技术**  
  [VmwareWorkstationPlayer下载地址](https://download3.vmware.com/software/player/file/VMware-player-16.1.2-17966106.exe?HashKey=9aecc8d36299c549f5393c12765ee5cb&ext=.exe&params=%7B%22sourcefilesize%22%3A%22215.30+MB%22%2C%22dlgcode%22%3A%220b8d0a237075b447cf7b1c896b201787%22%2C%22languagecode%22%3A%22cn%22%2C%22source%22%3A%22DOWNLOADS%22%2C%22downloadtype%22%3A%22manual%22%2C%22downloaduuid%22%3A%2260f2e47f-fe93-435f-b0c6-55906aed17b8%22%2C%22dlgtype%22%3A%22Product+Binaries%22%2C%22productversion%22%3A%2216.1.2%22%2C%22productfamily%22%3A%22VMware+Workstation+Player%22%7D&AuthKey=1627887609_f31caf12a5def6771412ef2ee38e7607&ext=.exe)

## 操作步骤
### A. 创建虚拟机
1. 创建新虚拟机

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/A-01.png)
2. 稍后安装操作系统  
   **也可以直接指定已有的镜像，当然之后顺不顺利就另说了**

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/A-02.png)
3. 选择Linux、Centos7 64位

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/A-03.png)
4. 为虚拟机命名，并指定安装位置

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/A-04.png)
5. 选择磁盘大小 ==（后期不可修改）==    
   **看自己实际需要，简单入门使用的话使用建议值20G已经很多了**

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/A-05.png)
6. 进入自定义硬件的部分 ==（后期均可修改）==

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/A-06.png)

**本人配置仅供参考**

项目 | 配置
---|---
内存 | 16G
处理器 | 12
网络适配器 | 桥接模式(复制物理网络状态)
新CD/DVD | 使用iso映像 **(手动指定镜像)**
不要USB控制器、声卡、打印机

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/A-07.png)  
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/A-08.png)

###### 补充说明
- ISO映像文件
> 需要是没有其他虚拟机使用的映像文件。虽然之前查过说可以多个虚拟机共享一个iso。但我实际操作一直不成功。
- 配置适配器
> 桥接模式使用的是自己物理机的网络状态，所以适配器就是选择自己的网卡（有线网卡和无线网卡）。有些网络问题就是网卡指定错了导致的，在这里mark一下

7. 创建完成

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/A-09.png)

### B. 运行虚拟机
1. 播放虚拟机

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-01.png)
2. 直接安装镜像

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-02.png)
3. 选择语言为中文、继续往下走  
   **VMware Tool是必须安装的 但是可以等虚拟机运行成功之后进行 否则耗时太长会心态爆炸**

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-03.png)
4. 设置安装信息

项目 | 值
---|---
软件选择 | 带GUI的服务器
安装位置 | 自动分区
网络选择 | 打开网络ensXX
> 其他配置项使用默认即可

**如果下决心要学Linux命令的，可以使用最小安装，到时候就没有用户界面了  
所以此处选择的是带GUI的服务器，当然选择GNOME桌面亦可**
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-04.png)

5. 设置root密码+创建用户
- 比如我设置root密码为root
- 创建用户henwy密码为henwy

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-05.png)
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-06.png)
6. 安装成功后重启

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-07.png)

7. 接受license

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-08.png)
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-09.png)
8. 检查网络设置
- 打开以太网ensXX
- 设置主机名为localhost。点击应用

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-10.png)
9. 完成配置  
   **即进入到登录页面**

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/B-11.png)
### C. 使用Linux
1. 鼠标右键打开终端

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/C-01.png)
2. 键入命令
- 查看当前ip地址
```
ifconfig
```
> ensXX部分即为当前桥接模式下，网络适配器分配的IP地址

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/C-02.png)

- 查看网络连通性
```
ping www.baidu.com
```
> 按Ctrl-C终止ping操作

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/C-03.png)
3. 重新安装VMware Tool

![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/C-04.png)
![image](https://raw.githubusercontent.com/HenwyGoal/tech-article/main/install/img/linux-install/C-05.png)
#### 至此，Linux虚拟机安装结束，并且能够访问外网，之后就是正常使用的事情了