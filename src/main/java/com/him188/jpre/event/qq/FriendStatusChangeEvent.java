package com.him188.jpre.event.qq;

import com.him188.jpre.OnlineStatus;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 好友在线状态改变
 *
 * @author Him188
 */
public class FriendStatusChangeEvent extends QQEvent {
	private final OnlineStatus status;

	public FriendStatusChangeEvent(RobotQQ robot, QQ qq,  OnlineStatus status) {
		super(robot, qq);
		this.status = status;
	}

	public OnlineStatus getStatus() {
		return status;
	}
}
