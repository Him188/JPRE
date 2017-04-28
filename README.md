
#JPRE  
——能让你使用 Java 开发 [MyPCQQ]("http://mypcqq.cc") 机器人插件的公益项目  
  
## 开源  
GitHub: [JPRE(Java)]("http://github.com/him188/jpre") / [MPQ插件(C#)]("http:/github.com/him188/jpre-mpq")  
  
## 下载
*项目仍在努力开发中，敬请期待*  
Circleci(需要登录): [JPRE(Java)]("baidu.com")  
Jenkins(直接下载): [JPRE(Java)]("baidu.com")  

## 作者  
**本项目最初由 @LamGC 发起，我(Him188)后来参加开发**  
- Java: @Him188(主要), @LamGC(部分), 其他朋友们(@XianD, @SoleMemory)
- C#: @Him188

## 词汇注明:   
- JPRE:  
指的就是本项目的 Java 部分(将在文章中讲解项目结构)，即 JavaPluginRuntimeEnvironment (翻译为中文: Java 插件运行环境)的简写    
- MPQ:  
MyPCQQ 的简写，MPQ是一款QQ机器人应用，其官方提供一个给出 API 的机器人框架，框架支持加载插件(此处暂不详解MPQ插件)，你可以在MPQ官网中获取更多详细帮助: [点击进入官网]("http://mypcqq.cc")   

## 结构  
*两个$包围的内容为随实际情况变化的目录*  
- JPRE 核心 Jar 放置位置: `$Nukkit根目录$/plugins/`  
- Nukkit JPRE 插件放置位置: `$Nukkit根目录$/plugins/`  
- JPRE 独立插件放置位置: `$Nukkit根目录$/plugins/jpre/jpreplugins/`  
- MPQ 插件放置位置:  `$MPQ根目录$/Plugin/`

## 说明  
   
#### 项目运行模式   
MPQ 官方框架支持 StdCall 方式的 Windows 动态链接库(DLL)，我们为了强劲的性能，选择了C#(感谢@somebody编写的MPQ C# 插件模板 [查看论坛原文]("TODO") )  
  
**项目核心工作方式为网络通讯(TCP)**  
JPRE为服务端，MPQ插件为客户端  
MPQ插件启动时连接JPRE，JPRE随即加载并启动插件  

**JPRE调用API会以网络数据包的形式传送到MPQ插件; MPQ插件收到事件会以同样的方式传递给JPRE**  
上述内容基本解释了JPRE与MPQ插件通信过程
*详细网络过程请参阅 Java: `com.him188.jpre.network`包; C#: `Plugin.Network`命名空间*

#### 详细说明
**特别提示**  
- 本文中所提到的所有 [插件] 若未明确标识，其意均指 MPQ 插件，而不是 Minecraft: Pocket Edition 服务端的插件  

**正文**  
*正文内容可能不适合不具备编程基础的读者阅读*   
  
JPRE 允许你使用 Java 调用 MPQ API，并且能处理 MPQ 发生的事件。  
JPRE的插件管理器，事件等与 Nukkit 服务端比较相似，若你熟悉 Nukkit 服务端插件开发，你也可以很快学会 JPRE 插件开发.  
从前几个标题中已经可以看出，JPRE是一个独立的应用程序，可以不依赖于其他应用运行  
为了使开发者能在 Nukkit 中简易地使用到 JPRE 提供的所有功能， 我们开发了 Nukkit 插件，插件的作用是加载 JPRE 核心 Jar，并开放 API 让其他 Nukkit 插件使用。

## 如何开发 JPRE 插件
待添加

- 提供示例插件: <a href="https://github.com/Him188/CQ-JPRE/">待添加</a>
- **详细开发文档**: <a href="https://github.com/Him188/CQ-JPRE/blob/master/Development.md">查看</a>
