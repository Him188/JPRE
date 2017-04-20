package com.him188.jpre.event.qq.taotao;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * @author Him188
 */
public class TaoTaoBeCommentedEvent extends TaoTaoEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public TaoTaoBeCommentedEvent(RobotQQ robot, QQ qq){
		super(robot, qq);
	}
}
