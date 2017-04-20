package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 群成员被解除禁言
 *
 * @author Him188
 */
public class GroupUnmuteEvent extends AdminGroupEvent {
	public GroupUnmuteEvent(RobotQQ robot, Group group, QQ qq, QQ admin) {
		super(robot, group, qq, admin);
	}
}
