package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;
import net.mamoe.jpre.event.Selectable;

/**
 * 他人请求添加机器人为好友
 *
 * @author Him188 @ JPRE Project
 */
@SuppressWarnings("unused")
public class FriendAddRequestEvent extends QQEvent implements Selectable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }


    public enum Action {
        ACCEPT_AND_ADD, //同意并添加为双向好友
        ACCEPT_ONLY,    //同意并添加为单向好友(对方好友有机器人, 机器人好友没有对方)
        DECLINE,        //拒绝
        IGNORE          //忽略
    }

    private String reason = ""; //拒绝时原因

    private final String message; //附加消息

    private Action action = Action.IGNORE;
    private final boolean isBad; //不良好友

    public FriendAddRequestEvent(RobotQQ robot, QQ qq, String message, boolean isBad) {
        super(robot, qq);
        this.message = message;
        this.isBad = isBad;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? "" : reason;
    }

    public String getMessage() {
        return message;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public boolean isBad() {
        return isBad;
    }

    @Override
    public int getResultStatus() {
        return super.getResultStatus();
    }
}
