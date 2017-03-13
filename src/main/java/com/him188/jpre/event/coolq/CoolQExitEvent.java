package com.him188.jpre.event.coolq;

import com.him188.jpre.event.HandlerList;

/**
 * @author Him188
 */
public class CoolQExitEvent extends CoolQEvent {
	private static final HandlerList handlers = new HandlerList();

	public CoolQExitEvent() {

	}

	public static HandlerList getHandlers() {
		return handlers;
	}
}
