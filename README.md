
# **该项目正在重写, 请耐心等待**
# **This project is rewriting, please wait**

##  JavaPluginRuntimeEnvironment  (JPRE)

##### Java插件运行环境 - 让你能使用 Java 开发MPQ插件

`
JPRE依赖于易语言版MPQ运行, 而不含有独立的机器人系统.
本项目有 2 个部分, 一个部分是你现在看到的 JPRE. 另一个部分是MPQ插件.  
MPQ插件启动的时候会通过TCP客户端与JPRE建立连接, 使JPRE加载Java插件.  
MPQ插件接受到MPQ事件后, 会以数据包的形式传递给JPRE, JPRE再依据事件优先级传递给Java插件.  
`

#### 丰富的MPQ功能支持  
易语言能做的, Java也能做  
1.  **表情** 所有表情的代码   
2.  **语音** 移植语音, 并提供直接接受语音到内存的便捷方法  
3.  **日志** 日志系统齐全, 可选择输出到运行环境日志或MPQ日志  
4.  **资料** 支持账号信息, 群员信息, 文件信息等的解析  

#### 强大的事件系统
相较MPQ, 我们拥有更强大的事件系统:
事件采用 “事件监视器” 进行注册和处理
1. **取消事件**  取消事件后其他事件监视器默认不接受事件
2. **拦截事件**  拦截事件是一种标识符
3. **方法标签**  采用方法标签方式声明事件监视器, 简单又好用
4. **简单注册** 一句代码即可自动注册整个类的事件检测器 (并且不需要在插件信息中定义)
5. **允许重复** 一个插件可以注册多个事件监视器来进行不同种类工作

#### 多样配置方法支持  
JPRE已经为开发者准备了配置保存方法, 目前支持的配置方法:
- Yaml (<a href="http://mvnrepository.com/artifact/org.yaml/snakeyaml">SnakeYAML</a>)
- Json (<a href="https://github.com/google/gson">Gson</a>)

#### 开发者支持
- 本项目是开源项目, 所有有关插件开发的类中都已写上详细的 Javadoc, 方便开发者参考.
- 提供示例插件: <a href="https://github.com/Him188/CQ-JPRE/">待添加</a>
- **详细开发文档**: <a href="https://github.com/Him188/CQ-JPRE/blob/master/Development.md">查看</a>
  
#### 协助我们
- 你的支持就是我们持续更新的动力, 如果你愿意并且条件允许, 请捐助任意数额到我的支付宝账号: Him188, 并备注JPRE, 谢谢！
- 在使用过程中遇到任何问题, 或是有好的建议, 请提交 issue, 或通过QQ与我们交谈:

#### 联系我们
- JPRE (主要开发): Him188  QQ1040400290
- MPQ插件(主要开发): LamGC  QQ807288702
