package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 私聊消息
 *
 * @author Him188
 */
public class PrivateMessageEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final String message;

	public PrivateMessageEvent(RobotQQ robot, QQ qq, String message) {
		super(robot, qq);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
