package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 某人被管理员/群主移出群
 *
 * @author Him188
 */
public class GroupKickEvent extends AdminGroupEvent{
	public GroupKickEvent(RobotQQ robot, Group group, QQ qq, QQ admin) {
		super(robot, group, qq, admin);
	}
}
