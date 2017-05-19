package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 被好友抖动
 *
 * @author Him188 @ JPRE Project */
public class FriendShakeEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FriendShakeEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
