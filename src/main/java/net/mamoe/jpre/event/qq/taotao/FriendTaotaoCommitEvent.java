package net.mamoe.jpre.event.qq.taotao;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;
import net.mamoe.jpre.event.qq.QQEvent;

/**
 * 好友评论机器人的说说 事件
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
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
