package com.him188.jpre.event.friend;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;

/**
 * 好友已添加事件
 *
 * @author Him188
 */
public class FriendAddEvent extends FriendEvent {
	private static final HandlerList handlers = new HandlerList();
	private final int type;
	private final long QQ;
	private final RobotQQ robot;

	public FriendAddEvent(RobotQQ robot, int type, long QQ) {
		this.robot = robot;
		this.type = type;
		this.QQ = QQ;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.FRIEND_ADD;
	}

	public int getType() {
		return type;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}

	public long getQQ() {
		return QQ;
	}
}
