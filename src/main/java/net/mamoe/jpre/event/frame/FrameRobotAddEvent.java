package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 添加了新 QQ
 *
 * @author Him188
 */
public class FrameRobotAddEvent extends FrameRobotEvent {
	private static final HandlerList handlers = new HandlerList();

	public FrameRobotAddEvent(Frame frame, RobotQQ robot) {
		super(frame,robot);
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

}
