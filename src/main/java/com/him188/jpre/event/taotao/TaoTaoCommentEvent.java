package com.him188.jpre.event.taotao;

import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public class TaoTaoCommentEvent extends TaoTaoEvent {
	private final RobotQQ robot;
	private final long targetQQ;
	private String message;

	public TaoTaoCommentEvent(RobotQQ robot, long targetQQ, String message){

		this.robot = robot;
		this.targetQQ = targetQQ;
		this.message = message;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public long getQQ() {
		return targetQQ;
	}
}
