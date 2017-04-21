package com.him188.jpre.event.qq;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.Event;

/**
 * @author Him188
 */
abstract public class SendMessageEvent extends Event {
	private final RobotQQ robot;

	public SendMessageEvent(RobotQQ robot){
		this.robot = robot;
	}

	public RobotQQ getRobot() {
		return robot;
	}
}
