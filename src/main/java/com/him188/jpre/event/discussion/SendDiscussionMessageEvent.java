package com.him188.jpre.event.discussion;

import com.him188.jpre.Discussion;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.qq.SendMessageEvent;

/**
 * @author Him188
 */
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
