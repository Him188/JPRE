package com.him188.jpre.event.friend;

import com.him188.jpre.RobotQQ;

/**
 * 好友签名变更
 *
 * @author Him188
 */
public class FriendSignChangeEvent extends FriendEvent {

	private final RobotQQ robot;
	private final long qq;
	private final String newSign;

	public FriendSignChangeEvent(RobotQQ robot, long QQ, String newSign){

		this.robot = robot;
		qq = QQ;
		this.newSign = newSign;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}

	@Override
	public long getQQ() {
		return qq;
	}

	public String getNewSign() {
		return newSign;
	}
}
