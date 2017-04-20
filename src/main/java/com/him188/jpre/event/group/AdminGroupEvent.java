package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public abstract class AdminGroupEvent extends GroupEvent {
	private final QQ admin;

	public AdminGroupEvent(RobotQQ robot, Group group, QQ qq, QQ admin) {
		super(robot, group, qq);
		this.admin = admin;
	}

	/**
	 * 获取进行操作的管理员
	 *
	 * @return 进行操作的管理员
	 */
	public QQ getAdmin() {
		return admin;
	}
}
