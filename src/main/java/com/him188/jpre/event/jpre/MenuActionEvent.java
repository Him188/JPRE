package com.him188.jpre.event.jpre;

import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public class MenuActionEvent extends JPREEvent {
	private final ActionType type;

	public enum ActionType {
		LEFT_CLICK,
		RIGHT_CLICK,
		LEFT_DOUBLE_CLICK
	}

	public MenuActionEvent(RobotQQ robot, ActionType type) {
		super(robot);
		this.type = type;
	}

	public ActionType getType() {
		return type;
	}
}
