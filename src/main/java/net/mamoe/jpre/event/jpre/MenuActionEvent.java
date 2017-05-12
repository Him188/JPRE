package net.mamoe.jpre.event.jpre;

import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 插件菜单被点击
 *
 * @author Him188
 * @deprecated 暂未支持
 */
@Deprecated
public class MenuActionEvent extends JPREEvent { // TODO: 2017/5/12
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


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
