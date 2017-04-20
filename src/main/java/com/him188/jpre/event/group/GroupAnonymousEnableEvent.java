package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 管理员开启了匿名聊天
 *
 * @author Him188
 */
public class GroupAnonymousEnableEvent extends GroupEvent {
	public GroupAnonymousEnableEvent(RobotQQ robot, Group group, QQ qq) {
		super(robot, group, qq);
	}
}
