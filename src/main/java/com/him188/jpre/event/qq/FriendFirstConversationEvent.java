package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 好友今日首次发起会话
 *
 * @author Him188
 */
public class FriendFirstConversationEvent extends QQEvent{
	public FriendFirstConversationEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
