package net.mamoe.jpre.event.qq.taotao;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.Cancellable;
import net.mamoe.jpre.event.HandlerList;

/**
 * 评论别人的说说
 *
 * @author Him188 @ JPRE Project
 * @deprecated 不支持
 */
@Deprecated
public class TaoTaoCommentEvent extends TaoTaoEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }


    private final String message;

    public TaoTaoCommentEvent(RobotQQ robot, QQ qq, String message) {
        super(robot, qq);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int getResultStatus() {
        return isCancelled() ? 1 : 0;
    }
}
