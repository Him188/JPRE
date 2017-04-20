package com.him188.jpre;

/**
 * @author Him188
 */
public class QQ extends User {

	private final long qq;

	public QQ(RobotQQ robot, long qq) {
		super(robot);
		this.qq = qq;
	}

	public long getQQ() {
		return qq;
	}

	@Override
	public boolean sendMessage(String content) {
		return getRobot().sendPrivateMessage(getQQ(), content) != 0;
	}

	@Override
	public String toString() {
		return "QQ(" + qq + ")";
	}
}
