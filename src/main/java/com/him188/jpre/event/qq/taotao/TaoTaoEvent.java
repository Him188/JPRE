package com.him188.jpre.event.qq.taotao;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.qq.QQEvent;

/**
 * @author Him188
 */
public abstract class TaoTaoEvent extends QQEvent {
	public TaoTaoEvent(RobotQQ robot, QQ qq){
		super(robot, qq);
	}
}
