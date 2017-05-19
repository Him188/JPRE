package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 管理员开启了匿名聊天
 *
 * @author Him188 @ JPRE Project */
public class GroupAnonymousEnableEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public GroupAnonymousEnableEvent(RobotQQ robot, Group group, QQ qq) {
		super(robot, group, qq);
	}
}
