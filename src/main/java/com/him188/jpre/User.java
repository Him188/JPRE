package com.him188.jpre;

/**
 * @author Him188
 */
abstract class User {
	abstract public boolean sendMessage(String content);

	private final RobotQQ robot;

	User(RobotQQ robot) {
		if (robot == null) {
			throw new IllegalArgumentException("Arg 'robot' cannot be null");
		}
		this.robot = robot;
	}

	public RobotQQ getRobot() {
		return robot;
	}


}
