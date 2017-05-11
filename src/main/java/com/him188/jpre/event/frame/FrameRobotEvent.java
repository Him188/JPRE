package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

import java.awt.*;

/**
 * @author Him188
 */
public abstract class FrameRobotEvent extends FrameEvent {

	private final RobotQQ robot;

	public FrameRobotEvent(RobotQQ robot) {
		this.robot = robot;
	}

	public RobotQQ getRobot() {
		return robot;
	}
}