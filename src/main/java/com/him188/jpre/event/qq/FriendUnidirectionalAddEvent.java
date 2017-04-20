package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 被单向添加好友
 *
 * @author Him188
 */
public class FriendUnidirectionalAddEvent extends QQEvent {
	public FriendUnidirectionalAddEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
