## **JPRE开发文档**

你可以在这里学习JPRE原理, JPRE插件结构和如何开发一个JPRE插件.
学习这些之前, 你需要先学会使用 <a href="http://baike.baidu.com/link?url=L539lkl2QvxL7HhJtoI2-37bkolNAFZAB1N0ZNJwUqgnOkHWTIUhxtQejilQ11OdNEUWgTshT5kw-zFFhwofv_">Java</a>.

### 目录
<a href="#jpre">一.JPRE原理</a>  
<a href="#plugin">二.JPRE插件结构</a>  
<a href="#develop">三.如何开发JPRE插件</a>


### <span id="jpre" name="jpre">第一章 JPRE原理</span>
**简述**  
- JPRE依赖于易语言版酷Q运行, 而不含有独立的机器人系统.  
本项目有 2 个部分, 一个部分就是你现在看到的——JPRE. 还有一个部分是酷Q插件.   
酷Q插件启动的时候会通过 易语言Java支持库 运行JPRE的Jar包, JPRE加载Java插件.  

**Java 与酷Q 通讯方式**  
- JPREMain中有这一个方法:  
`public static String getLog()`  
JPRE的日志系统记录的所有日志, 都会存放在 LogManager 中  
该方法可以获取并删除 LogManager 中的第一条日志  
酷Q插件中有一个线程, 会不停地调用该方法, 来达到日志传输的目的
  
- 那JPRE中使用到的酷Q APi, 如发送消息, 发送赞是怎样传递给酷Q的?  
答案很简单, 就是类似于日志的方式:  
`public static String getCommand()`

**酷Q 与 Java 通讯方式**  
- 前面已经提到了易语言Java支持库, 易语言Java支持库到底是什么?  
这个支持库可以运行Jar包, 构造类, 执行类方法或是执行类静态方法.  
酷Q插件 与 Java 通讯方式就是通过执行静态方法实现的.  
JPREMain 中的大部分静态方法都是提供给酷Q插件调用的.    
下面列举酷Q插件会调用到的方法:  
`init(ILjava/lang/String;)`  
`callEvent(I[Ljava/lang/Object;)V`  
`getCommand()Ljava/lang/String;`  
`getLog()Ljava/lang/String;`  
`setCommandResult(Ljava/lang/String;)`  
`setCommandResult(I)`  
`loadPluginDescription(Ljava/lang/String;)Z`  
`getPluginName(Ljava/lang/String;)Ljava/lang/String;`  
`getPluginVersion(Ljava/lang/String;)Ljava/lang/String;`  
`getPluginAuthor(Ljava/lang/String;)Ljava/lang/String;`  
`getPluginDescription(Ljava/lang/String;)Ljava/lang/String;`  
`getPluginAPI(Ljava/lang/String;)I`  
`loadPlugin(Ljava/lang/String;)Z`  
`setAuthCode(I)V`  
`enablePlugin(Ljava/lang/String;)Z`  
`disablePlugin(Ljava/lang/String;)Z`

### <span id="plugin" name="plugin">第二章 JPRE插件结构</span>
**简述**  
- JPRE插件都是 Jar 包
- Jar 包根目录必须有一个声明插件信息的文件. 它的名字可以是: 
  `plugin.json` `cq.json` `jpre.json`  
  该文件必须存在以下字段:  
  - `name`: 插件名  
  - `author`: 插件作者  
  - `api`: 插件API版本号, 目前酷Q的API版本为9, 若插件API版本高于酷Q的API版本, 那么该插件将无法加载  
  - `version`: 插件版本. 请使用 "1.0.0" 格式来书写版本号.  
  - `main`: 插件主类的类全名(包含包名). 插件加载时会加载主类并调用 onLoad()V    

  可选字段:  
  - `description`: 插件的说明.  
 
**主类**  
- JPRE插件的主类必须实现接口 Plugin  
JPRE内部已经内置了类 JavaPlugin, 该类已经实现 Plugin 接口,  
并集成了酷Q API, 我们推荐插件继承类 JavaPlugin, 这样可以节省开发时间, 最大化简化插件开发过程.  

**事件**  
- JPRE插件与酷Q插件不同, 酷Q由于局限于易语言的机制, 事件系统不强.  
JPRE拥有自由度高, 支持动态监听, 拦截, 取消的事件系统
- 事件监听器  
    - 一个事件监听器可以监听一个事件.
    - 事件监听器可以是一个方法. 该方法需要包含以下特性:
      - 带有注解 `@EventHandler`, 详细请查看源码 `net.coding.lamgc.coolq.jpre.event.EventHandler`  
      - 有且只有一个参数, 参数类型为任何一个 `Event` (继承 `Event`, 例如 `GroupMessageEvent`)  
      - 返回值不限. 可为 void 也可为其他任意类型  
      - 是否静态不限 
      - 是否公共不限
      - 不带有注解 `@Deprecated`. 若监听器带有该注解, 注册时会失败.
    - 例子:  
    \@EventHandler  
    public void onMessage(GroupMessageEvent event){    
    &emsp;&emsp;System.out.println("收到了群消息"+event.getMessage());  
    }  
    
- 监听一个事件  
  - 方法1: 使用 `PluginManager#registerEvents(Listener, Plugin)`, JPRE自动搜寻Listener中所有符合规范的事件监听器并注册  
  - 方法2: 使用 `PluginManager#registerEvent(Listener, Plugin, Class<Event>, Method)` 手动注册事件监听器.
  - 注意, 插件未启用时不可以注册事件. 即onLoad时不可以注册事件, onEnable时可以.


### <span id="develop" name="develop">第三章 如何开发一个JPRE插件</span>
- 待补充