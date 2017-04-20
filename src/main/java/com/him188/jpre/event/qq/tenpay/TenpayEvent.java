package com.him188.jpre.event.qq.tenpay;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.qq.QQEvent;

/**
 * 财付通事件
 *
 * @author Him188
 */
abstract public class TenpayEvent extends QQEvent {
	public TenpayEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
