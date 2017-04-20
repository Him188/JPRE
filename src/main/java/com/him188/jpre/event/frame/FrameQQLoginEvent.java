package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * QQ 登录完成
 *
 * @author Him188
 */
public class FrameQQLoginEvent extends FrameQQEvent {
	public FrameQQLoginEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
