package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 被删除好友
 *
 * @author Him188
 */
public class FriendDeleteEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FriendDeleteEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
