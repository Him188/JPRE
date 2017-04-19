package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.RobotEvent;

/**
 * @author Him188
 */
abstract public class QQEvent extends RobotEvent {
	private QQ qq;

	public QQEvent(RobotQQ robot, QQ qq) {
		super(robot);
		this.qq = qq;
	}

	public QQ getQq() {
		return qq;
	}
}
