package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 群公告变更
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class GroupNotificationChangeEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final String newNotification;

	public GroupNotificationChangeEvent(RobotQQ robot, Group group, QQ qq, String newNotification) {
		super(robot, group, qq);
		this.newNotification = newNotification;
	}

	public String getNewNotification() {
		return newNotification;
	}
}
