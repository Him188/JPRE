package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 某人成为/被取消管理员
 *
 * @author Him188
 */
public class GroupAdminChangeEvent extends GroupEvent {
	public enum ChangeType {
		PROMOTION, //成为管理员
		DEMOTION,  //降职为普通成员
	}

	private final ChangeType type;

	public GroupAdminChangeEvent(RobotQQ robot, Group group, QQ qq, ChangeType type) {
		super(robot, group, qq);
		this.type = type;
	}

	public ChangeType getType() {
		return type;
	}
}
