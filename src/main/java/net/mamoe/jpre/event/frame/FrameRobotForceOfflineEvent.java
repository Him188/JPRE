package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.Frame;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * QQ 被强制离线
 *
 * @author Him188
 */
public class FrameRobotForceOfflineEvent extends FrameRobotEvent {
    private static final HandlerList handlers = new HandlerList();

    public FrameRobotForceOfflineEvent(Frame frame, RobotQQ robot) {
        super(frame,robot);
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
}
