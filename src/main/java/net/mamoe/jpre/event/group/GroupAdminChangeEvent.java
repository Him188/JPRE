package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 某人成为/被取消管理员
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class GroupAdminChangeEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public enum ChangeType {
		PROMOTION, //成为管理员
		DEMOTION,  //降职为普通成员
	}

	private final ChangeType type;

	public GroupAdminChangeEvent(RobotQQ robot, Group group, QQ qq, ChangeType type) {
		super(robot, group, qq);
		this.type = type;
	}

	public ChangeType getType() {
		return type;
	}
}
