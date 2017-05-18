package net.mamoe.jpre.event.jpre;

import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.Cancellable;
import net.mamoe.jpre.event.HandlerList;

/**
 * 插件菜单被点击
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */// TODO: 2017/5/18
public class MenuActionEvent extends JPREEvent implements Cancellable{
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
