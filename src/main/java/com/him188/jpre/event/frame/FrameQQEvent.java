package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public abstract class FrameQQEvent extends FrameEvent {
	private final QQ qq;
	private final RobotQQ robotQQ;

	public FrameQQEvent(RobotQQ robot, QQ qq) {
		this.robotQQ = robot;
		this.qq = qq;
	}

	public RobotQQ getRobotQQ() {
		return robotQQ;
	}

	public QQ getQQ() {
		return qq;
	}
}
