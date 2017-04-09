package com.him188.jpre.event.message;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;

/**
 * 讨论组消息接收事件
 *
 * @author Him188
 */
public class DiscussMessageEvent extends MessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_DISCUSS = 1;
	private static final HandlerList handlers = new HandlerList();
	public final long QQ;
	public final int type;
	public final long discuss;
	public final RobotQQ robot;
	public String message; //收到的消息
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public DiscussMessageEvent(RobotQQ robot, int type, long discuss, long QQ, String message) {
		this.robot = robot;
		this.type = type == TYPE_DISCUSS ? type : TYPE_UNKNOWN;
		this.QQ = QQ;
		this.discuss = discuss;
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
		return EventTypes.DISCUSS_MESSAGE;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public long getDiscuss() {
		return discuss;
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

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
}
