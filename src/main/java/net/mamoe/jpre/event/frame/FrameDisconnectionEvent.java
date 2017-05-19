package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.event.HandlerList;

/**
 * 框架断开连接 JPRE 时触发.
 *
 * @author Him188 @ JPRE Project */ // TODO: 2017/5/18 implement
public class FrameDisconnectionEvent extends FrameEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}

	public FrameDisconnectionEvent(Frame frame) {
		super(frame);
	}
}
