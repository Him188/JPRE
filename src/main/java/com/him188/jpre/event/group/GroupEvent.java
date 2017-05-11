package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.qq.QQEvent;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public abstract class GroupEvent extends QQEvent {

	private final Group group;

	public GroupEvent(RobotQQ robot, Group group, QQ qq) {
		super(robot, qq);
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}
}
