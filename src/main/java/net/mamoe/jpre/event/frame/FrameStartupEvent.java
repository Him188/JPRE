package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.event.HandlerList;

/**
 * 框架加载完成
 *
 * @author Him188 @ JPRE Project */
public class FrameStartupEvent extends FrameEvent {
	private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public FrameStartupEvent(Frame frame) {
        super(frame);
    }

}
