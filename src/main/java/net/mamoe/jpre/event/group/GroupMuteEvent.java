package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 群成员被禁言
 *
 * @author Him188 @ JPRE Project
 */
public class GroupMuteEvent extends GroupAdminEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final int time;

	public GroupMuteEvent(RobotQQ robot, Group group, QQ qq, QQ admin, int time) {
		super(robot, group, qq, admin);
		this.time = time;
	}

	/**
	 * 获取被禁言时间, 单位秒
	 * @return 被禁言时间
	 */
	public int getTime() {
		return time;
	}
}
