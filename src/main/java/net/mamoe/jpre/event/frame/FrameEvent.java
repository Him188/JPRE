package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.event.Event;

/**
 * @author Him188 @ JPRE Project */
public abstract class FrameEvent extends Event{
    private final Frame frame;

    public FrameEvent(Frame frame) {
        this.frame = frame;
    }

    public Frame getFrame() {
        return frame;
    }
}
