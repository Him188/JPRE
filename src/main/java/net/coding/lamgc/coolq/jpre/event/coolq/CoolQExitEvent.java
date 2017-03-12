package net.coding.lamgc.coolq.jpre.event.coolq;

import net.coding.lamgc.coolq.jpre.event.HandlerList;

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
