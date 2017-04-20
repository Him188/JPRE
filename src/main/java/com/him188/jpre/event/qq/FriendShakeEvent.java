package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 被好友抖动
 *
 * @author Him188
 */
public class FriendShakeEvent extends QQEvent {
	public FriendShakeEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
