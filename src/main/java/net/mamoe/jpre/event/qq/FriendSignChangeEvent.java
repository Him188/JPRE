package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 好友个性签名变更
 *
 * @author Him188 @ JPRE Project */
public class FriendSignChangeEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final String content;

	public FriendSignChangeEvent(RobotQQ robot, QQ qq, String content) {
		super(robot, qq);
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
