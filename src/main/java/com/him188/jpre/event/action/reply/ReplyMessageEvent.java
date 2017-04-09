package com.him188.jpre.event.action.reply;

import com.him188.jpre.event.action.ActionEvent;

/**
 * @author Him188
 */
abstract public class ReplyMessageEvent extends ActionEvent {

	abstract public long getQQ();

	abstract public String getMessage();

	//abstract public void setMessage(String message);

	abstract public String getRepeat();

	abstract public void setRepeat(String repeat);


}



