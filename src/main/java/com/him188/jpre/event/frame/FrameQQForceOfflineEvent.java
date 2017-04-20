package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * QQ 被强制离线
 *
 * @author Him188
 */
public class FrameQQForceOfflineEvent extends FrameQQEvent {
	public FrameQQForceOfflineEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
