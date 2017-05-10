package com.him188.jpre.event.frame;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 框架加载完成
 *
 * @author Him188
 */
public class FrameStartupEvent extends FrameEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}

}
