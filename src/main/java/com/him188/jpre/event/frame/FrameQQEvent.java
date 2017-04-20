package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public abstract class FrameQQEvent extends FrameEvent {
	private final QQ qq;

	public FrameQQEvent(RobotQQ robot, QQ qq) {
		super(robot);
		this.qq = qq;
	}

	public QQ getQQ() {
		return qq;
	}
}
