package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;
import net.mamoe.jpre.event.Selectable;

/**
 * 某人被邀请加入群
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class GroupInvitationRequestEvent extends GroupEvent implements Selectable {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}

	private Action action = Action.IGNORE;

	private final QQ invitee;

	public GroupInvitationRequestEvent(RobotQQ robot, Group group, QQ qq, QQ invitee) {
		super(robot, group, qq);
		this.invitee = invitee;
	}

	/**
	 * 获取被邀请的人
	 *
	 * @return 被邀请的人
	 */
	public QQ getInvitee() {
		return invitee;
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
