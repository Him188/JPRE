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

	public long getNumber() {
		return qq;
	}

	@Override
	public boolean sendMessage(String content) {
		return getRobot().sendPrivateMessage(this, content);
	}

	@Override
	public String toString() {
		return "QQ(" + qq + ")";
	}
}
