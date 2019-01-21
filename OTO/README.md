# ge-oto

#### 项目介绍

#### 软件架构
软件架构说明

#### 编译说明
1. 生产war包编译：mvn -DskipTests=true package -Pproduct
2. 测试war包编译：mvn -DskipTests=true package -Ptest


#### 安装教程
1. xxx
2. xxx
3. xxx

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 部署说明

1. 
2. 
3. 
4. 

####wkhtmltopdf再linuxx下需要依赖
yum -y install  libXrender* libfontconfig* libXext*

#### QA
1. 若是pdf下载中文不显示：是因为linux系统缺少中文字体，将 SIMSUN.TTC 字体库放到 /usr/share/fonts 路径下
