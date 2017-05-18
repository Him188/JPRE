package net.mamoe.jpre;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public abstract class User { // TODO: 2017/5/17 Group/DiscussionTemporary 类
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


}