package com.him188.jpre;

/**
 * @author Him188
 */
public class Discussion extends User {
	private final long discussion;

	public Discussion(RobotQQ robot, long discussion){
		super(robot);
		this.discussion = discussion;
	}

	public long getDiscussion() {
		return discussion;
	}

	@Override
	public boolean sendMessage(String content) {// TODO: 2017/4/19 return value
		return getRobot().sendDiscussMessage(this.discussion, content) != 0;
	}
}
