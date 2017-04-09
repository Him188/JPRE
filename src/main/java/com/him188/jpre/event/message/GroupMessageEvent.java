package com.him188.jpre.event.message;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;

/**
 * 群消息接收事件
 *
 * @author Him188
 */
public class GroupMessageEvent extends MessageEvent {
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long QQ;
	public final long group;
	public final RobotQQ robot;
	public String message; //收到的消息
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public GroupMessageEvent(RobotQQ robot, int type, long group, long QQ, String message) {
		this.robot = robot;
		this.type = type;
		this.QQ = QQ;
		this.group = group;

		this.message = message;
	}

	@Override
	public RobotQQ getRobot() {
		return null;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.GROUP_MESSAGE;
	}

	public long getGroup() {
		return this.group;
	}

	public int getType() {
		return this.type;
	}


	public long getQQ() {
		return this.QQ;
	}


	public String getMessage() {
		return this.message;
	}

	/*public void setMessage(String message) {
		this.message = message;
	}*/



	public String getRepeat() {
		return this.repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
}
