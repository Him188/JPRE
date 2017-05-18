package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.event.HandlerList;

/**
 * 框架连接(包括重连) JPRE 时触发.
 *
 *
 * @author Him188
 */
public class FrameLoginEvent extends FrameEvent {
	private static final HandlerList handlers = new HandlerList();

	public FrameLoginEvent(Frame frame) {
		super(frame);
	}

	public static HandlerList getHandlers() {
		return handlers;
	}
}
