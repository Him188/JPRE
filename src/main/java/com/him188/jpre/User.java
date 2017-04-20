package com.him188.jpre;

/**
 * @author Him188
 */
abstract class User {
	abstract public boolean sendMessage(String content);

	private final RobotQQ robot;

	User(RobotQQ robot) {
		this.robot = robot;
	}

	public RobotQQ getRobot() {
		return robot;
	}


}
