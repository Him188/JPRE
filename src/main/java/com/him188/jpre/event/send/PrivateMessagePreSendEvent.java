package com.him188.jpre.event.send;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;

/**
 * @author Him188
 */
public class PrivateMessagePreSendEvent extends SendMessageEvent {
	private static final HandlerList handlers = new HandlerList();
	public final long QQ;
	public String message;
	public final RobotQQ robot;

	public PrivateMessagePreSendEvent(RobotQQ robot, long QQ, String message) {
		this.robot = robot;
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
		return EventTypes.PLUGIN_ACTION_SEND_PRIVATE;
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
