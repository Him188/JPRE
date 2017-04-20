package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 好友今日首次发起会话
 *
 * @author Him188
 */
public class FriendFirstConversationEvent extends QQEvent{
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FriendFirstConversationEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
