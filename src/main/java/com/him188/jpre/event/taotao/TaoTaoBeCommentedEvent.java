package com.him188.jpre.event.taotao;

import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public class TaoTaoBeCommentedEvent extends TaoTaoEvent {
	private final RobotQQ robot;
	private final long qq;

	public TaoTaoBeCommentedEvent(RobotQQ robot, long qq){

		this.robot = robot;
		this.qq = qq;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}

	@Override
	public long getQQ() {
		return qq;
	}
}
