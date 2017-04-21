package com.him188.jpre;

/**
 * @author Him188
 */
public class Discussion extends User {
	private final long number;

	public Discussion(RobotQQ robot, long number){
		super(robot);
		this.number = number;
	}

	public long getNumber() {
		return number;
	}

	@Override
	public boolean sendMessage(String content) {// TODO: 2017/4/19 return value
		return getRobot().sendDiscussionMessage(this, content);
	}
}
