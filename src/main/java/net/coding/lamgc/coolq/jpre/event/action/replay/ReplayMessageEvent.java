package net.coding.lamgc.coolq.jpre.event.action.replay;

import net.coding.lamgc.coolq.jpre.event.action.ActionEvent;

/**
 * @author Him188
 */
abstract public class ReplayMessageEvent extends ActionEvent {
	abstract public int getTime();

	abstract public String getMessage();

	abstract public String getRepeat();
}
