package com.example.demo;

import com.him188.jpre.plugin.JavaPlugin;
import com.him188.jpre.PluginManager;

/**
 * 酷Q Java应用平台
 * Java应用开发示例
 *
 * 可在本示例基础上开发应用
 */
public class PluginMain extends JavaPlugin {


    /**
     * 可以用main方法测试插件功能或给插件实现一些其他功能
     * @param args 运行参数
     */
    public static void main(String[] args){
        System.out.println("Hello!CoolQ JPRE!");
    }

    /**
     * 插件载入
     * 当JPRE将插件载入时，如果载入成功，将会调用本方法
     * 注意！请不要在本方法注册监听器类
     */
    public void onLoad(){
        getLogger().info("酷Q JPRE示例插件","应用载入成功！");

    }

    /**
     * 插件启用
     * 当插件启用时，本方法将被调用
     * 可在本方法注册监听器
     */
    public void onEnable(){
        //注册监听器类【Plugin_EventHandler】，该类实现Listener接口
        PluginManager.registerEvents(new Plugin_EventHandler(),this);
        getLogger().info("酷Q JPRE示例插件","应用已启用！事件监听器注册完成！");
    }

    /**
     * 插件停用
     * 当插件停用时，本方法将被调用
     */
    public void onDisable() {
    }
}
