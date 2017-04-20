package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 某人申请加入群
 *
 * @author Him188
 */
public class GroupJoinRequestEvent extends GroupEvent {
	public GroupJoinRequestEvent(RobotQQ robot, Group group, QQ qq) {
		super(robot, group, qq);
	}
}
