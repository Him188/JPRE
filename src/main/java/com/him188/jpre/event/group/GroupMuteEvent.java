package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 群成员被禁言
 *
 * @author Him188
 */
public class GroupMuteEvent extends AdminGroupEvent{
	private final int time;

	public GroupMuteEvent(RobotQQ robot, Group group, QQ qq, QQ admin, int time) {
		super(robot, group, qq, admin);
		this.time = time;
	}

	/**
	 * 获取被禁言时间, 单位秒
	 * @return 被禁言时间
	 */
	public int getTime() {
		return time;
	}
}
