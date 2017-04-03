package com.example.demo;

//监听器相关
import com.him188.jpre.event.Listener;
import com.him188.jpre.event.EventHandler;
import com.him188.jpre.event.friend.FriendAddEvent;
import com.him188.jpre.event.group.GroupAdminChangeEvent;
import com.him188.jpre.event.group.GroupFileUploadEvent;
import com.him188.jpre.event.group.GroupMemberDecreaseEvent;
import com.him188.jpre.event.group.GroupMemberIncreaseEvent;
import com.him188.jpre.event.message.DiscussMessageEvent;
import com.him188.jpre.event.message.GroupMessageEvent;

import com.him188.jpre.Code;
import com.him188.jpre.event.message.PrivateMessageEvent;
import com.him188.jpre.event.request.AddFriendRequestEvent;
import com.him188.jpre.event.request.AddGroupRequestEvent;
import com.him188.jpre.plugin.config.YamlConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 事件监听器
 * 在这里处理事件
 *
 * 本类预先写好了几个常用的事件监听器，可以自己添加或删除
 */
public class Plugin_EventHandler implements Listener {

    /**
     * 群消息事件
     * @param event 事件参数
     */
    @EventHandler
    public void onGroupMessage(GroupMessageEvent event){
        /*
         * 签到示例
         * Yaml配置使用示例
         * 感觉用了个愚蠢的时间判断方法讷23333
         */
        if(event.getMessage().equals("签到")){
            //获取签到QQ的YamlConfig对象
            YamlConfig yc = new YamlConfig(new PluginMain().getDataFolder() + "sign\\" + String.valueOf(event.getQQ()) + ".yml");
            //获取最后签到时间
            String[] t = yc.getString("time").split("-");

            //创建SimpleDateFormat类 置时间获取格式为 年年-月月-日日
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            //获取时间并按格式输出
            String f = sdf.format(new Date());
            //分割时间
            String[] ft = f.split("-");

            //置签到状态
            boolean sign_stats = false;
            //循环判断时间，由年开始到日
            for(int i = 0;i != 2;i++) {
                //如果可以，就签到成功
                if (Integer.getInteger(t[i]) < Integer.getInteger(ft[i])) {
                    //发送信息
                    event.setRepeat(Code.at(event.getQQ()) + "签到成功！最新签到时间：" + f);
                    //写入最新签到时间
                    yc.set("time",t);
                    //记得保存配置信息23333
                    yc.save();
                    //置成功签到
                    sign_stats = true;
                    //跳出循环
                    break;
                }
            }
            //判断是否签到失败
            if(!(sign_stats)){
                //签到失败的消息
                event.setRepeat(Code.at(event.getQQ()) + "你已经签到过了！");
            }

            //拦截事件
            //拦截事件后，其他应用将无法收到本次事件，可以防止消息指令被重复处理
            event.isIntercepted();

            //还有一种拦截事件的方法，但不推荐使用
            // 【取消事件】 也能达到 【拦截事件】 的效果，但取消事件后，插件的事件也会不处理，即 抛弃这个事件，不进行处理
            //event.isCancelled();
        }
        //复述消息
        event.setRepeat(Code.at(event.getQQ()) + "你刚刚发送了这条消息："  + event.getMessage());
    }

    /**
     * 私聊消息事件
     * @param event 事件参数
     */
    @EventHandler
    public void onPrivateMessage(PrivateMessageEvent event){
        event.setRepeat("你刚刚发送了这条消息：" + event.getMessage());
    }

    /**
     * 讨论组事件
     * @param event 事件参数
     */
    @EventHandler
    public void onDiscussMessage(DiscussMessageEvent event){

    }

    /**
     * 群文件上传事件
     * @param event 事件参数
     */
    @EventHandler
    public void onGroupFileUpload(GroupFileUploadEvent event){

    }

    /**
     * 群事件 - 管理员变动
     * @param event 事件参数
     */
    @EventHandler
    public void onGroupAdminChange(GroupAdminChangeEvent event){

    }

    /**
     * 群事件 - 群成员减少
     * @param event 事件参数
     */
    @EventHandler
    public void onGroupMemberDecrease(GroupMemberDecreaseEvent event){

    }

    /**
     * 群事件 - 群成员添加
     * @param event 事件参数
     */
    @EventHandler
    public void onGroupMemberIncrease(GroupMemberIncreaseEvent event){

    }

    /**
     * 好友事件 - 好友已添加
     * @param event 事件参数
     */
    @EventHandler
    public void onFriend_Add(FriendAddEvent event){

    }

    /**
     * 请求 - 好友添加
     * @param event 事件参数
     */
    @EventHandler
    public void onRequest_AddFriend(AddFriendRequestEvent event){

    }

    /**
     * 请求 - 群添加
     * @param event 事件参数
     */
    @EventHandler
    public void onRequest_AddGroup(AddGroupRequestEvent event){

    }
}
