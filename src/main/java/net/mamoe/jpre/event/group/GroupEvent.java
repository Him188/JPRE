package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.qq.QQEvent;

/**
 * @author Him188 @ JPRE Project */
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
