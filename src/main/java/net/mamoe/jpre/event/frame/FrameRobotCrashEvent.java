package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * QQ 长时间无响应或掉线
 *
 * @author Him188 @ JPRE Project */
public class FrameRobotCrashEvent extends FrameRobotEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}

	public FrameRobotCrashEvent(Frame frame, RobotQQ robot) {
		super(frame,robot);
	}
}
