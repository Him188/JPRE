package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 某人退出了群
 *
 * @author Him188
 */
public class GroupQuitEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public GroupQuitEvent(RobotQQ robot, Group group, QQ qq) {
		super(robot, group, qq);
	}
}
