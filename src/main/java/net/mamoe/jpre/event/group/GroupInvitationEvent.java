package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.Action;
import net.mamoe.jpre.event.HandlerList;
import net.mamoe.jpre.event.Selectable;

/**
 * 机器人被邀请加入群
 *
 * @author Him188 @ JPRE Project
 */
public class GroupInvitationEvent extends GroupEvent implements Selectable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private Action action = Action.IGNORE;

    public GroupInvitationEvent(RobotQQ robot, Group group, QQ qq) {
        super(robot, group, qq);
    }

    @Override
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public int getResultStatus() {
        return action.getIntVal();
    }

}
