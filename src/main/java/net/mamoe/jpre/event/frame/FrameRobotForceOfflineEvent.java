package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * QQ 被强制离线
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class FrameRobotForceOfflineEvent extends FrameRobotEvent {
    private static final HandlerList handlers = new HandlerList();

    public FrameRobotForceOfflineEvent(RobotQQ robot) {
        super(robot);
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
}
