package com.him188.jpre.event.friend;

import com.him188.jpre.RobotQQ;

/**
 * 好友在线状态变更
 *
 * @author Him188
 */
public class FriendStatusChangeEvent extends FriendEvent {

	private final RobotQQ robot;
	private final long qq;
	private final int status;// TODO: 2170/4/10  enum OnlineStatus

	public FriendStatusChangeEvent(RobotQQ robot, long QQ, int status){

		this.robot = robot;
		qq = QQ;
		this.status = status;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}

	@Override
	public long getQQ() {
		return qq;
	}

	public int getStatus() {
		return status;
	}
}
