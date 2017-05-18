package net.mamoe.jpre.event;

import net.mamoe.jpre.RobotQQ;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
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
