package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 群聊消息
 *
 * @author Him188
 */
public class GroupMessageEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final String message;

	public GroupMessageEvent(RobotQQ robot, Group group, QQ qq, String message) {
		super(robot, group, qq);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
