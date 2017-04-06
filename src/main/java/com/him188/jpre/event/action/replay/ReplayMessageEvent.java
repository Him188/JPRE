package com.him188.jpre.event.action.replay;

import com.him188.jpre.event.action.ActionEvent;
import com.him188.jpre.infomation.Font;

/**
 * @author Him188
 */
abstract public class ReplayMessageEvent extends ActionEvent {

	abstract public long getQQ();

	abstract public String getMessage();

	//abstract public void setMessage(String message);

	abstract public String getRepeat();

	abstract public void setRepeat(String repeat);

	abstract public int getTime();

	abstract public Font getFont();
}



