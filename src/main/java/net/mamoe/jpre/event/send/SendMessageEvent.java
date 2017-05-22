package net.mamoe.jpre.event.send;

import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.Event;

/**
 * @author Him188 @ JPRE Project
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
