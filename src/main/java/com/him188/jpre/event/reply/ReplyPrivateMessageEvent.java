package com.him188.jpre.event.reply;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.message.PrivateMessageEvent;

/**
 * 回复私聊消息事件
 *
 * @author Him188
 */
public class ReplyPrivateMessageEvent extends ReplyMessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long QQ;
	public final RobotQQ robot;
	public String message;
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public ReplyPrivateMessageEvent(PrivateMessageEvent event) {
		this(event.robot, event.type, event.QQ, event.message);
		this.repeat = event.getRepeat();
	}

	public ReplyPrivateMessageEvent(RobotQQ robot, int type, long QQ, String message) {
		this.robot = robot;
		this.type = type;
		this.QQ = QQ;
		this.message = message;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.PLUGIN_ACTION_REPLAY_PRIVATE;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public int getType() {
		return type;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getQQ() {
		return QQ;
	}
}


