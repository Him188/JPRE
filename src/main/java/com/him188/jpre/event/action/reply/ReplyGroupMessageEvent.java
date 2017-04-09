package com.him188.jpre.event.action.reply;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.message.GroupMessageEvent;

/**
 * 回复群消息事件
 *
 * @author Him188
 */
public class ReplyGroupMessageEvent extends ReplyMessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_GROUP = 1;
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long QQ;
	public final long group;
	public final RobotQQ robot;
	public final String message; //收到的消息
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public ReplyGroupMessageEvent(GroupMessageEvent event) {
		this(event.robot, event.type,  event.group, event.QQ, event.message);
		this.repeat = event.getRepeat();
	}

	public ReplyGroupMessageEvent(RobotQQ robot, int type, long group, long QQ, String message) {
		this.robot =robot;
		this.type = type == TYPE_GROUP ? type : TYPE_UNKNOWN;
		this.QQ = QQ;
		this.group = group;
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
		return EventTypes.PLUGIN_ACTION_REPLAY_GROUP;
	}


	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public int getType() {
		return this.type;
	}



	public String getRepeat() {
		return this.repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getMessage() {
		return this.message;
	}

	public long getQQ() {
		return this.QQ;
	}

	public long getGroup() {
		return this.group;
	}
}
