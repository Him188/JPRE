package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 好友评论机器人的说说 事件
 *
 * @author Him188
 */
public class FriendTaotaoCommitEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final String content;

	public FriendTaotaoCommitEvent(RobotQQ robot, QQ qq, String content) {
		super(robot, qq);
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
