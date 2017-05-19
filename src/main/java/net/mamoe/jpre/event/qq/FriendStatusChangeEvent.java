package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.OnlineStatus;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 好友在线状态改变
 *
 * @author Him188 @ JPRE Project */
public class FriendStatusChangeEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final OnlineStatus status;

	public FriendStatusChangeEvent(RobotQQ robot, QQ qq,  OnlineStatus status) {
		super(robot, qq);
		this.status = status;
	}

	public OnlineStatus getStatus() {
		return status;
	}
}
