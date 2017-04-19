package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 好友个性签名变更
 *
 * @author Him188
 */
public class FriendSignChangeEvent extends QQEvent {
	private final String content;

	public FriendSignChangeEvent(RobotQQ robot, QQ qq, String content) {
		super(robot, qq);
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
