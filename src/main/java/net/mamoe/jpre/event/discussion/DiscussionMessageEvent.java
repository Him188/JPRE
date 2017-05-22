package net.mamoe.jpre.event.discussion;

import net.mamoe.jpre.Discussion;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 讨论组消息. 一些消息如颜文字的特殊符号收到时会被转义为 unicode.
 *
 * @author XianD @ JPRE Project
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

