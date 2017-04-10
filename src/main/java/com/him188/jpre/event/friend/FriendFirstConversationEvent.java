package com.him188.jpre.event.friend;

import com.him188.jpre.RobotQQ;

/**
 * 好友今天首次发起会话
 *
 * @author Him188
 */
public class FriendFirstConversationEvent extends FriendEvent {
	private final RobotQQ robot;
	private final long qq;

	public FriendFirstConversationEvent(RobotQQ robot, long qq) {
		this.robot = robot;
		this.qq = qq;
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
