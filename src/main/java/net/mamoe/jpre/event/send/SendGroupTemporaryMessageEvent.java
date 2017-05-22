package net.mamoe.jpre.event.send;

import net.mamoe.jpre.GroupTemporary;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * @author Him188 @ JPRE Project
 */
public class SendGroupTemporaryMessageEvent extends SendMessageEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }


    private final GroupTemporary session;
    private final String message;

    public SendGroupTemporaryMessageEvent(RobotQQ robotQQ, GroupTemporary session, String message) {
        super(robotQQ);
        this.session = session;
        this.message = message;
    }

    public GroupTemporary getSession() {
        return session;
    }

    public String getMessage() {
        return message;
    }
}
