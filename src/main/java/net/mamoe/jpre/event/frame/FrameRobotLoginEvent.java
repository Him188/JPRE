package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * QQ 登录完成
 *
 * @author Him188
 */
public class FrameRobotLoginEvent extends FrameRobotEvent {
	private static final HandlerList handlers = new HandlerList();

	public FrameRobotLoginEvent(RobotQQ robot) {
		super(robot);
	}

	public static HandlerList getHandlers() {
		return handlers;
	}
}
