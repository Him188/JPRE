package com.him188.jpre.event.discussion;

import com.him188.jpre.Discussion;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.qq.QQEvent;

/**
 * @author XianD @ jpre Project
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
