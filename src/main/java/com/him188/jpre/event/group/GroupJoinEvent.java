package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 某人被同意加入群
 *
 * @author Him188
 */
public class GroupJoinEvent extends AdminGroupEvent {
	public GroupJoinEvent(RobotQQ robot, Group group, QQ qq, QQ admin) {
		super(robot, group, qq, admin);
	}
}
