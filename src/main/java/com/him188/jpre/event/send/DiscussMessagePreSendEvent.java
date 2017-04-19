package com.him188.jpre.event.send;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.EventTypes;

/**
 * @author Him188
 */
public class DiscussMessagePreSendEvent extends SendMessageEvent {
	private static final HandlerList handlers = new HandlerList();
	public final long discuss;
	public String message;

	public final RobotQQ robot;

	public DiscussMessagePreSendEvent(RobotQQ robot, long discuss, String message) {
		this.robot = robot;
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
		return EventTypes.PLUGIN_ACTION_SEND_DISCUSS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getDiscuss() {
		return discuss;
	}
}
