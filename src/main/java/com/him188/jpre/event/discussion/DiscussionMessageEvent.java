package com.him188.jpre.event.discussion;

import com.him188.jpre.Discussion;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 讨论组消息
 * @author XianD @ jpre Project
 * @since JPRE 1.0.0
 */
public class DiscussionMessageEvent extends DiscussionEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final String message;

    public DiscussionMessageEvent(RobotQQ robot, Discussion discussion, QQ qq, String message) {
        super(robot, discussion, qq);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

