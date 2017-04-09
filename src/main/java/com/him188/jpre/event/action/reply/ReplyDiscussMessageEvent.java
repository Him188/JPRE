package com.him188.jpre.event.action.reply;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.message.DiscussMessageEvent;

/**
 * 回复讨论组消息事件
 *
 * @author Him188
 */
public class ReplyDiscussMessageEvent extends ReplyMessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_DISCUSS = 1;
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long QQ;
	public final long discuss;
	public final String message; //收到的消息
	public final RobotQQ robot;
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public ReplyDiscussMessageEvent(DiscussMessageEvent event) {
		this(event.robot, event.type, event.discuss, event.QQ, event.message);
		this.repeat = event.getRepeat();
	}

	public ReplyDiscussMessageEvent(RobotQQ robot, int type, long discuss, long QQ, String message) {
		this.robot = robot;
		this.type = type == TYPE_DISCUSS ? type : TYPE_UNKNOWN;
		this.QQ = QQ;
		this.discuss = discuss;
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
		return EventTypes.PLUGIN_ACTION_REPLAY_DISCUSS;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public String getMessage() {
		return message;
	}

	public int getType() {
		return type;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public long getQQ() {
		return QQ;
	}

	public long getDiscuss() {
		return discuss;
	}
}
