package com.him188.jpre.event.qq;

import com.him188.jpre.OnlineStatus;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public class FriendStatusChangeEvent extends QQEvent {
	private final QQ friend;
	private final OnlineStatus status;

	public FriendStatusChangeEvent(RobotQQ robot, QQ qq, QQ friend, OnlineStatus status) {
		super(robot, qq);
		this.friend = friend;
		this.status = status;
	}

	public QQ getFriend() {
		return friend;
	}

	public OnlineStatus getStatus() {
		return status;
	}
}
