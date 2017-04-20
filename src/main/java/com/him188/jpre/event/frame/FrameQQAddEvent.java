package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 添加了新 QQ
 *
 * @author Him188
 */
public class FrameQQAddEvent extends FrameQQEvent {
	public FrameQQAddEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
