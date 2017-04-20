package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 某人被邀请加入群
 *
 * @author Him188
 */
public class GroupInvitationRequestEvent extends GroupEvent {
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
}
