package com.him188.jpre.event.send;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.Event;

/**
 * @author Him188
 */
abstract public class SendMessageEvent extends Event {
	abstract public RobotQQ getRobot();
	abstract public String getMessage();
}
