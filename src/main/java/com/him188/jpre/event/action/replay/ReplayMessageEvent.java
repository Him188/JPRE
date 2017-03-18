package com.him188.jpre.event.action.replay;

import com.him188.jpre.event.action.ActionEvent;

/**
 * @author Him188
 */
abstract public class ReplayMessageEvent extends ActionEvent {
	abstract public int getTime();

	abstract public String getMessage();

	abstract public String getRepeat();
}



