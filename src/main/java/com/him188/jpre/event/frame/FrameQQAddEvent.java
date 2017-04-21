package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 添加了新 QQ
 *
 * @author Him188
 */
public class FrameQQAddEvent extends FrameQQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}

	public FrameQQAddEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
