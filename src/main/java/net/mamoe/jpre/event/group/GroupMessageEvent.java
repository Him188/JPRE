package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 群聊消息
 * Called when receive group message
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class GroupMessageEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final String message;

	public GroupMessageEvent(RobotQQ robot, Group group, QQ qq, String message) {
		super(robot, group, qq);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
