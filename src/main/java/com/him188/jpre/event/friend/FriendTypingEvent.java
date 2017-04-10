package com.him188.jpre.event.friend;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 好友正在输入
 *
 * @author Him188
 */
public class FriendTypingEvent extends FriendEvent {
	private static final HandlerList handlers = new HandlerList();
	private final RobotQQ robot;
	private final long qq;

	public static HandlerList getHandlers() {
		return handlers;
	}

	public FriendTypingEvent(RobotQQ robot, long QQ){
		this.robot = robot;
		qq = QQ;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}

	@Override
	public long getQQ() {
		return qq;
	}
}
