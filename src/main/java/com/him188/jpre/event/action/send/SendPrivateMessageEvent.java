package com.him188.jpre.event.action.send;

import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.EventTypes;

/**
 * @author Him188
 */
public class SendPrivateMessageEvent extends SendMessageEvent {
	private static final HandlerList handlers = new HandlerList();
	public final long QQ;
	public String message;

	public SendPrivateMessageEvent(long QQ, String message) {
		this.QQ = QQ;
		this.message = message;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.PLUGIN_ACTION_SEND_PRIVATE;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getQQ() {
		return QQ;
	}
}
