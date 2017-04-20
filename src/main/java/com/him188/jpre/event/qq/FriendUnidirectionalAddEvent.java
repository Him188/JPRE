package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 被单向添加好友
 *
 * @author Him188
 */
public class FriendUnidirectionalAddEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FriendUnidirectionalAddEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
