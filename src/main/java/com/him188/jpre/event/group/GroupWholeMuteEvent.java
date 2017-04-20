package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 管理员开启了全群禁言
 *
 * @author Him188
 */
public class GroupWholeMuteEvent extends GroupEvent {
	public GroupWholeMuteEvent(RobotQQ robot, Group group, QQ qq) {
		super(robot, group, qq);
	}
}
