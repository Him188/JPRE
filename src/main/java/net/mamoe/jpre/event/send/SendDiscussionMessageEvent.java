package net.mamoe.jpre.event.send;

import net.mamoe.jpre.Discussion;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * @author Him188 @ JPRE Project */
public class SendDiscussionMessageEvent extends SendMessageEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final Discussion discussion;
	private final String message;

	public SendDiscussionMessageEvent(RobotQQ robotQQ, Discussion discussion, String message) {
		super(robotQQ);
		this.discussion = discussion;
		this.message = message;
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public String getMessage() {
		return message;
	}
}
