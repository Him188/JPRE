package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 被删除好友
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class FriendDeleteEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FriendDeleteEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
