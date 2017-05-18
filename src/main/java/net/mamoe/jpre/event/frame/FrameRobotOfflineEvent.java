package net.mamoe.jpre.event.frame;

import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 主动使 QQ 离线
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class FrameRobotOfflineEvent extends FrameRobotEvent {
	private static final HandlerList handlers = new HandlerList();

	public FrameRobotOfflineEvent(RobotQQ robot) {
		super(robot);
	}

	public static HandlerList getHandlers() {
		return handlers;
	}
}
