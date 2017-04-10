package com.him188.jpre.event.taotao;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.Event;

/**
 * @author Him188
 */
public abstract class TaoTaoEvent extends Event {
	abstract public RobotQQ getRobot();

	abstract public long getQQ();
}
