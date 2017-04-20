package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * QQ 长时间无响应或掉线
 *
 * @author Him188
 */
public class FrameQQCrashEvent extends FrameQQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FrameQQCrashEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
