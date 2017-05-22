package net.mamoe.jpre;

/**
 * @author Him188 @ JPRE Project
 */
public abstract class User {
	abstract public boolean sendMessage(String content);

	abstract public boolean sendObjectMessage(String content, String subType);

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

	abstract public long getNumber();
}
