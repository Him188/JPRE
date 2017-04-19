package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 被删除好友
 *
 * @author Him188
 */
public class FriendDeleteEvent extends QQEvent {
	public FriendDeleteEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
