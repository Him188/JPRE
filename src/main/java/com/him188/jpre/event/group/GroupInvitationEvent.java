package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 机器人被邀请加入群
 *
 * @author Him188
 */
public class GroupInvitationEvent extends GroupEvent {
	public GroupInvitationEvent(RobotQQ robot, Group group, QQ qq) {
		super(robot, group, qq);
	}
}
