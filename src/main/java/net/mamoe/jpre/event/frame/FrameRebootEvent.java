package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.event.HandlerList;

/**
 * 框架即将重启
 *
 * @author Him188
 */
public class FrameRebootEvent extends FrameEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}
}
