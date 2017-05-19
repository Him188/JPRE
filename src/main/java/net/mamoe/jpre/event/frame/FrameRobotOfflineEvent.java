package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 主动使 QQ 离线
 *
 * @author Him188 @ JPRE Project */
public class FrameRobotOfflineEvent extends FrameRobotEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public FrameRobotOfflineEvent(Frame frame, RobotQQ robot) {
        super(frame,robot);
    }

}
