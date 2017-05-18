package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 某人退出了群
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
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
