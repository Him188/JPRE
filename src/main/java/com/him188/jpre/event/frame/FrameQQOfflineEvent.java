package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 主动使 QQ 离线
 *
 * @author Him188
 */
public class FrameQQOfflineEvent extends FrameQQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public FrameQQOfflineEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
