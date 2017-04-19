package com.him188.jpre.event.qq.taotao;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public class TaoTaoCommentEvent extends TaoTaoEvent {
	private final String message;

	public TaoTaoCommentEvent(RobotQQ robot, QQ qq, String message) {
		super(robot, qq);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
