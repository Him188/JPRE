package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * QQ 被强制离线
 *
 * @author Him188
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
