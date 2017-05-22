package net.mamoe.jpre.event.send;

import net.mamoe.jpre.DiscussionTemporary;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * @author Him188 @ JPRE Project
 */
public class SendDiscussionTemporaryMessageEvent extends SendMessageEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }


    private final DiscussionTemporary session;
    private final String message;

    public SendDiscussionTemporaryMessageEvent(RobotQQ robotQQ, DiscussionTemporary session, String message) {
        super(robotQQ);
        this.session = session;
        this.message = message;
    }

    public DiscussionTemporary getSession() {
        return session;
    }

    public String getMessage() {
        return message;
    }
}
