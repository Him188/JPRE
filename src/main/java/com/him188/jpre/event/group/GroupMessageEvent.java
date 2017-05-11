package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 群聊消息
 * Called when receive group message
 *
 * @author Him188
 * @since JPRE 1.0.0
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
