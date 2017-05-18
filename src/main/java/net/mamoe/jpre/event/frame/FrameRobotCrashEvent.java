package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * QQ 长时间无响应或掉线
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class FrameRobotCrashEvent extends FrameRobotEvent {
	private static final HandlerList handlers = new HandlerList();

	public FrameRobotCrashEvent(RobotQQ robot) {
		super(robot);
	}

	public static HandlerList getHandlers() {
		return handlers;
	}
}
