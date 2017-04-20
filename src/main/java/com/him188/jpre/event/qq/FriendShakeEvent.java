package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 被好友抖动
 *
 * @author Him188
 */
public class FriendShakeEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FriendShakeEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
