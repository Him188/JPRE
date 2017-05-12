package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

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