package com.him188.jpre.event;

import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
abstract public class RobotEvent extends Event {
	private RobotQQ robot;

	public RobotEvent(RobotQQ robot){
		this.robot = robot;
	}

	public RobotQQ getRobot() {
		return robot;
	}
}
