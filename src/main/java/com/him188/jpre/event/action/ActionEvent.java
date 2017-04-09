package com.him188.jpre.event.action;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.Event;

/**
 * 插件操作事件. 如: 主动发送信息, 被动回复信息, 处理好友请求...
 *
 * @author Him188
 */
abstract public class ActionEvent extends Event {
	public abstract RobotQQ getRobot();
}
