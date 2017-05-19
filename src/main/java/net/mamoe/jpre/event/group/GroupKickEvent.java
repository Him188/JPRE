package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 某人被管理员/群主移出群
 *
 * @author Him188 @ JPRE Project */
public class GroupKickEvent extends GroupAdminEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public GroupKickEvent(RobotQQ robot, Group group, QQ qq, QQ admin) {
		super(robot, group, qq, admin);
	}
}
