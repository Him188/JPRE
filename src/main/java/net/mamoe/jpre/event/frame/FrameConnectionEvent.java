package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.event.HandlerList;

/**
 * 框架连接(包括重连) JPRE 时触发.
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class FrameConnectionEvent extends FrameEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}

	public FrameConnectionEvent(Frame frame) {
		super(frame);
	}
}
