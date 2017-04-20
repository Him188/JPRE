package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public class PrivateMessageEvent extends QQEvent {
	private final String message;

	public PrivateMessageEvent(RobotQQ robot, QQ qq, String message) {
		super(robot, qq);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
