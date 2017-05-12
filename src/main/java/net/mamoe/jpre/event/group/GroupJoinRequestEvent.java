package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 某人申请加入群
 *
 * @author Him188
 */
public class GroupJoinRequestEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public GroupJoinRequestEvent(RobotQQ robot, Group group, QQ qq) {
		super(robot, group, qq);
	}
}
