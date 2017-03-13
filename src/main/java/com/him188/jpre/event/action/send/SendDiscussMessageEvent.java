package com.him188.jpre.event.action.send;

import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.EventTypes;

/**
 * @author Him188
 */
public class SendDiscussMessageEvent extends SendMessageEvent {
	private static final HandlerList handlers = new HandlerList();
	public final long discuss;
	public String message;

	public SendDiscussMessageEvent(long discuss, String message) {
		this.discuss = discuss;
		this.message = message;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.PLUGIN_ACTION_SEND_DISCUSS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getDiscuss() {
		return discuss;
	}
}
