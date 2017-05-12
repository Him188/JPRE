package net.mamoe.jpre.event.discussion;

import net.mamoe.jpre.Discussion;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.qq.QQEvent;

/**
 * @author XianD @ JPRE Project
 * @since JPRE 1.0.0
 */
public abstract class DiscussionEvent extends QQEvent {

    private final Discussion discussion;

    public DiscussionEvent(RobotQQ robot, Discussion discussion, QQ qq) {
        super(robot, qq);
        this.discussion = discussion;

    }

    public Discussion getDiscussion() {
        return discussion;
    }
}
