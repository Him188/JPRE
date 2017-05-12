package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.RobotQQ;

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