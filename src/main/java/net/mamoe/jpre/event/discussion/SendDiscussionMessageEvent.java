package net.mamoe.jpre.event.discussion;

import net.mamoe.jpre.Discussion;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.Cancellable;
import net.mamoe.jpre.event.HandlerList;

/**
 * 发送讨论组消息事件
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 * @deprecated 未支持 // TODO: 2017/5/12 完成讨论组消息事件
 */
@Deprecated
public class SendDiscussionMessageEvent extends DiscussionEvent implements Cancellable{
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }


    private final Discussion discussion;
    private final String message;

    public SendDiscussionMessageEvent(RobotQQ robotQQ, Discussion discussion, QQ qq, String message) {
        super(robotQQ, discussion, qq);
        this.discussion = discussion;
        this.message = message;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public String getMessage() {
        return message;
    }
}
