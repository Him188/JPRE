package com.him188.jpre.event.frame;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 主动使 QQ 离线
 *
 * @author Him188
 */
public class FrameQQOfflineEvent extends FrameQQEvent {
	public FrameQQOfflineEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
