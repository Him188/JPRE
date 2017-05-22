package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 私聊消息. 一些消息如颜文字的特殊符号收到时会被转义为 unicode.
 *
 * @author Him188 @ JPRE Project
 */
public class FriendMessageEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final String message;

	public FriendMessageEvent(RobotQQ robot, QQ qq, String message) {
		super(robot, qq);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
