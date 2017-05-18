package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.event.HandlerList;

/**
 * 框架加载完成
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class FrameRebootEvent extends FrameEvent {
	private static final HandlerList handlers = new HandlerList();

    public FrameRebootEvent(Frame frame) {
        super(frame);
    }

    public static HandlerList getHandlers() {
		return handlers;
	}

}
