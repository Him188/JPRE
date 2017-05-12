package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * @author Him188
 */
public class SendGroupMessageEvent extends SendMessageEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final Group group;
	private final String message;

	public SendGroupMessageEvent(RobotQQ robotQQ, Group group, String message) {
		super(robotQQ);
		this.group = group;
		this.message = message;
	}

	public Group getGroup() {
		return group;
	}

	public String getMessage() {
		return message;
	}
}
