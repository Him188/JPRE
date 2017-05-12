package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * @author Him188
 */
public class SendPrivateMessageEvent extends SendMessageEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final QQ qq;
	private final String message;

	public SendPrivateMessageEvent(RobotQQ robotQQ, QQ qq, String message) {
		super(robotQQ);
		this.qq = qq;
		this.message = message;
	}

	public QQ getQQ() {
		return qq;
	}

	public String getMessage() {
		return message;
	}
}
