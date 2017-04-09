package com.him188.jpre.event.message;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;

/**
 * 私聊消息接收事件
 *
 * @author Him188
 */
public class PrivateMessageEvent extends MessageEvent {
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long QQ;
	public final RobotQQ robot;
	public String message; //收到的消息
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复
	public final int messageType;


	public static final int MESSAGE_TYPE_PRIVATE = 1;             //私聊消息
	public static final int MESSAGE_TYPE_DISCUSS_TEMPORARY_SESSION = 2;     //群临时消息
	public static final int MESSAGE_TYPE_GROUP_TEMPORARY_SESSION = 3;   //讨论组临时消息

	public PrivateMessageEvent(RobotQQ robot, int messageType, int type, long QQ, String message) {
		this.robot = robot;
		this.messageType = messageType;
		this.type = type;
		this.QQ = QQ;
		this.message = message;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.PRIVATE_MESSAGE;
	}

	public int getMessageType() {
		return messageType;
	}

	public int getType() {
		return type;
	}

	public long getQQ() {
		return QQ;
	}

	public String getMessage() {
		return message;
	}

	/*public void setMessage(String message) {
		this.message = message;
	}*/

	public String getRepeat() {
		return repeat;
	}

	@SuppressWarnings("SameParameterValue")
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
}
