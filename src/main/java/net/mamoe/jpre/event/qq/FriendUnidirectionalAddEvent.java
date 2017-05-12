package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 被单向添加好友
 *
 * @author Him188
 */
public class FriendUnidirectionalAddEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FriendUnidirectionalAddEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
