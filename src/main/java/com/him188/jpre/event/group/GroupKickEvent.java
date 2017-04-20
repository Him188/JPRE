package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 某人被管理员/群主移出群
 *
 * @author Him188
 */
public class GroupKickEvent extends AdminGroupEvent{
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public GroupKickEvent(RobotQQ robot, Group group, QQ qq, QQ admin) {
		super(robot, group, qq, admin);
	}
}
