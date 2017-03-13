package com.him188.jpre.event;

/**
 * @author Him188
 */
public interface EventTypes {
	//以下事件由酷Q创建:

	int EXIT = 1002; //酷Q退出

	int DISABLE = 1004; //应用(JPRE)将被停用

	int PRIVATE_MESSAGE = 21; //私聊消息
	int GROUP_MESSAGE = 2; //群消息
	int DISCUSS_MESSAGE = 4; //讨论组消息

	int GROUP_UPLOAD = 11; //群文件上传
	int GROUP_ADMIN = 101; //管理员变动
	int GROUP_MEMBER_DECREASE = 102; //群成员减少
	int GROUP_MEMBER_INCREASE = 103; //群成员增加

	int FRIEND_ADD = 201; //好友已添加
	int REQUEST_FRIEND_ADD = 301; //请求-好友添加
	int REQUEST_GROUP_ADD = 302; //请求-群添加

	//以下事件由Java插件创建:
	int PLUGIN_BASE = 10000; //请勿使用
	int PLUGIN_ENABLE = PLUGIN_BASE + 1; //Java插件启动
	int PLUGIN_DISABLE = PLUGIN_BASE + 2; //Java插件关闭

	int PLUGIN_ACTION_REPLAY_GROUP = PLUGIN_BASE + 3; //Java插件回复群消息
	int PLUGIN_ACTION_REPLAY_DISCUSS = PLUGIN_BASE + 4; //Java插件回复讨论组消息
	int PLUGIN_ACTION_REPLAY_PRIVATE = PLUGIN_BASE + 5; //Java插件回复好友私聊消息

	int PLUGIN_ACTION_SEND_GROUP = PLUGIN_BASE + 6; //Java插件主动发送群消息
	int PLUGIN_ACTION_SEND_DISCUSS = PLUGIN_BASE + 7; //Java插件主动发送讨论组消息
	int PLUGIN_ACTION_SEND_PRIVATE = PLUGIN_BASE + 8; //Java插件主动发送好友私聊消息
}


