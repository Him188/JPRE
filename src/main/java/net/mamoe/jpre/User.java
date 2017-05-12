package net.mamoe.jpre;

/**
 * @author Him188
 */
public abstract class User {
	abstract public boolean sendMessage(String content);

	private final RobotQQ robot;

	User(RobotQQ robot) {
		if (robot == null) {
			throw new NullPointerException("robot");
		}
		this.robot = robot;
	}

	public RobotQQ getRobot() {
		return robot;
	}


}
