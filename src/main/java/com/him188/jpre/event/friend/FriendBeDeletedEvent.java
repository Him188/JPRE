package com.him188.jpre.event.friend;

import com.him188.jpre.RobotQQ;

/**
 * 被删除好友
 *
 * @author Him188
 */
public class FriendBeDeletedEvent extends FriendEvent {

	private final RobotQQ robot;
	private final long qq;

	public FriendBeDeletedEvent(RobotQQ robot, long QQ){

		this.robot = robot;
		qq = QQ;
	}


	@Override
	public long getQQ() {
		return qq;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}
}
