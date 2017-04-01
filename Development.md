## **JPRE开发文档**

你可以在这里学习JPRE原理, JPRE插件结构和如何开发一个JPRE插件.
学习这些之前, 你需要先学会使用 <a href="http://baike.baidu.com/link?url=L539lkl2QvxL7HhJtoI2-37bkolNAFZAB1N0ZNJwUqgnOkHWTIUhxtQejilQ11OdNEUWgTshT5kw-zFFhwofv_">Java</a>.

### 目录
<a href="#第一章-jpre原理">一.JPRE原理</a>  
<a href="#第二章-jpre插件结构">二.JPRE插件结构</a>  
<a href="#develop">三.如何开发JPRE插件</a>


## <span id="第一章-JPRE原理" name="第一章-JPRE原理">第一章 JPRE原理</span>
**简述**  
- JPRE依赖于易语言版酷Q运行, 而不含有独立的机器人系统.  
本项目有 2 个部分, 一个部分就是你现在看到的——JPRE. 还有一个部分是酷Q插件.   
注意！酷Q插件和JPRE都是独立运行的, 他们之间通过网络进行通讯, JPRE的运行需要JRE/JDK 8, 如果你没有, 请点击[这里](https://www.java.com/zh_CN/download/manual.jsp)根据操作系统获取最新的Jre8.  
酷Q插件启动的时候会通过 TCP网络 连接JPRE, JPRE加载Java插件.  

**Java 与酷Q 通讯方式**  
- Java等到酷Q插件连接上并且通过了身份验证后, 将会开始接收来自酷Q插件的事件数据包, 同时将运行在JPRE上所有已启用应用的操作通过数据包发送给酷Q插件, 让酷Q插件执行.  

**酷Q 与 Java 通讯方式**  
- 在酷Q启用插件时, 酷Q插件会尝试使用配置文件中的连接信息与JPRE进行连接, 并进行身份验证, 验证成功, 酷Q插件将开始进入正常工作, 此时酷Q插件将接收所有事件, 并将事件以网络数据包的形式发送给JPRE, 由JPRE将事件分发给所有Java插件处理.  
- 当酷Q插件与JPRE 连接/身份验证 失败时, 可手动在酷Q插件的菜单, 单击 "重新连接JPRE" 重新尝试连接, 本项可以在修改了配置文件中的连接信息后进行重连.  
- **注意: 在连接成功后, 修改配置文件中的连接信息不会影响当前与目前连接成功的JPRE的连接.**  

## <span id="plugin" name="plugin">第二章 JPRE插件结构</span>
**简述**  
- JPRE插件都是 Jar 包
- Jar 包根目录必须有一个声明插件信息的文件. 它的名字可以是: 
  `plugin.json` `cq.json` `jpre.json`  
  该文件必须存在以下字段:  
  - `name`: (String) 插件名  
  - `author`: (String) 插件作者  
  - `api`: (int) 插件API版本号, 目前酷Q的API版本为9, 若插件API版本高于酷Q的API版本, 那么该插件将无法加载  
  - `version`: (String) 插件版本. 推荐使用 "1.0.0" 格式来书写版本号.  
  - `main`: (String) 插件主类的类全名(包含包名). 插件加载时会加载主类并调用 onLoad()    

  可选字段:  
  - `description`: (String) 插件的说明.  
 
**主类**  
- JPRE插件的主类必须实现接口 Plugin  
JPRE内置了类 JavaPlugin, 该类已经实现 Plugin 接口, 并集成了酷Q API和jar包资源文件读取, 我们推荐插件继承类 JavaPlugin, 这样可以节省开发时间, 最大化简化插件开发过程.  
  - 例子:  
  
  public class TestPluginMain extends PluginBase{  
  &emsp;&emsp;\@Override  
  &emsp;&emsp;public void onLoad(){  
  &emsp;&emsp;&emsp;&emsp;getLogger().info("普通", "插件正在加载!");  
  &emsp;&emsp;}  
  }  
  

**事件**  
- JPRE插件与酷Q插件不同, 酷Q由于局限于静态库机制, 监听事件必须在插件信息中定义.  
JPRE拥有自由度高, 支持动态监听, 拦截, 取消的事件系统
- 事件监听器  
    - 一个事件监听器可以监听一个事件.  
    - 事件监听器可以是一个方法. 该方法需要包含以下特性:  
      - 带有注解 `@EventHandler`, 详细请查看源码 `com.him188.jpre.event.EventHandler`  
      - 有且只有一个参数, 参数类型为任何一个 `Event` (继承 `Event`, 例如 `GroupMessageEvent`)  
      - 返回值不限. 可为 `void` 也可为其他任意类型  
      (**注意: 必须使用 `Event` 内的方法使事件取消或拦截, 返回值 `boolean`没有任何作用**)
      - 是否静态不限  
      - 不带有注解 `@Deprecated`. 若监听器带有该注解, 注册时会失败.
    - 例子:  
    \@EventHandler  
    public void onMessage(GroupMessageEvent event){    
    &emsp;&emsp;System.out.println("收到了群消息"+event.getMessage());  
    }  
    
- 监听一个事件  
  - 方法1: 使用 `PluginManager#registerEvents(Listener, Plugin)`, JPRE自动搜寻`Listener` 中所有符合规范的事件监听器并注册  
  - 方法2: 使用 `PluginManager#registerEvent(Listener, Plugin, Class<Event>, Method)` 手动注册事件监听器.
  - 注意, 插件未启用时不可以注册事件. 即 `onLoad` 时不可以注册事件, `onEnable` 时可以.


## <span id="develop" name="develop">第三章 如何开发一个JPRE插件</span>
1. 首先, 前往JPRE在 GitHub 上的 releases, 下载已编译好的最新版JPRE.jar (点击[这里](https://github.com/Him188/CQ-JPRE/releases/tag/1.0.0)下载) .  
2. 把 JPRE.jar 添加到项目依赖库中.  
3. 让插件主类继承 `JavaPlugin` 类("class xxx extends JavaPlugin").  
4. 创建一个新的类, 并让这个类实现 `Listener` 接口("class xxx implements Listener"), 这个类将作为事件监听类使用.  
5. 在插件主类可选添加 `onLoad`, `onEnable`, `onDisable` 方法, 他们用途分别是 插件被载入 插件被启用 插件被停用.  
6. 在 `onEnable` 添加代码:  `PluginManager#registerEvents(Listener, Plugin)` 来注册事件监听器类(`onLoad` 中不允许注册事件), 使用这个方法, JPRE将自动搜索符合条件 (条件请查阅<a href="#第二章-jpre插件结构">JPRE插件结构</a> 事件篇) 的事件监听器, 并注册他们, 当事件监听器被注册后, 就可以正常接收事件并对他们进行处理.   
7. 发挥你的创造力!
