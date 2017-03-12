package net.coding.lamgc.coolq.jpre.event.action.send;

import net.coding.lamgc.coolq.jpre.event.EventTypes;
import net.coding.lamgc.coolq.jpre.event.HandlerList;

/**
 * @author Him188
 */
public class SendGroupMessageEvent extends SendMessageEvent {
	private static final HandlerList handlers = new HandlerList();
	public final long group;
	public String message;

	public SendGroupMessageEvent(long group, String message) {
		this.group = group;
		this.message = message;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.PLUGIN_ACTION_SEND_GROUP;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getGroup() {
		return group;
	}
}
