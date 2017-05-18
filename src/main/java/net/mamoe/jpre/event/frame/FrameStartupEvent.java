package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.event.HandlerList;

/**
 * 框架加载完成
 *
 * @author Him188
 */
public class FrameStartupEvent extends FrameEvent {
	private static final HandlerList handlers = new HandlerList();

    public FrameStartupEvent(Frame frame) {
        super(frame);
    }

    public static HandlerList getHandlers() {
		return handlers;
	}

}
