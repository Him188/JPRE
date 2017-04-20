package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 群被群主解散
 * <p>
 * 本类中 {@link #getQQ()} 返回群主的 QQ
 *
 * @author Him188
 */
public class GroupDissolutionEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public GroupDissolutionEvent(RobotQQ robot, Group group, QQ qq) {
		super(robot, group, qq);
	}
}