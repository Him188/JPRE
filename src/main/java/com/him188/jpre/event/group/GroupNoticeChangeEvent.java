package com.him188.jpre.event.group;

import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * @author Him188
 */
public class GroupNoticeChangeEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlers() {
		return handlers;
	}

	private final RobotQQ robot;
	private final long group;
	private final long modifier;
	private final String context;

	public GroupNoticeChangeEvent(RobotQQ robot, long group, long modifier, String context){

		this.robot = robot;
		this.group = group;
		this.modifier = modifier;
		this.context = context;
	}

	public RobotQQ getRobot() {
		return robot;
	}

	@Override
	public long getGroup() {
		return group;
	}

	public long getModifier() {
		return modifier;
	}

	public String getContext() {
		return context;
	}
}
