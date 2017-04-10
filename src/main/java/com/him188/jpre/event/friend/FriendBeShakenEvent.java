package com.him188.jpre.event.friend;

import com.him188.jpre.RobotQQ;

/**
 * 被好友抖动
 *
 * @author Him188
 */
public class FriendBeShakenEvent extends FriendEvent {
	private final RobotQQ robot;
	private final long qq;

	public FriendBeShakenEvent(RobotQQ robot, long qq) {

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
