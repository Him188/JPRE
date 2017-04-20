package com.him188.jpre.event.frame;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 框架即将重启
 *
 * @author Him188
 */
public class FrameRebootEvent extends FrameEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FrameRebootEvent(RobotQQ robot) {
		super(robot);
	}
}
