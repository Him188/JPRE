package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * QQ 长时间无响应或掉线
 *
 * @author Him188
 */
public class FrameQQCrashEvent extends FrameQQEvent {
	public FrameQQCrashEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
