package net.mamoe.jpre.event.jpre;

import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 插件菜单被点击
 *
 * @author Him188 @ JPRE Project
 */
public class MenuActionEvent extends JPREEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }


    private final ActionType type;

    public enum ActionType {
        LEFT_CLICK(1),
        RIGHT_CLICK(2),
        LEFT_DOUBLE_CLICK(3);

        private int type;

        ActionType(int type) {
            this.type = type;
        }

        public static ActionType fromInt(int type) {
            for (ActionType actionType : ActionType.values()) {
                if (actionType.type == type) {
                    return actionType;
                }
            }
            return null;
        }
    }

    public MenuActionEvent(RobotQQ robot, ActionType type) {
        super(robot);
        this.type = type;
    }

    public ActionType getType() {
        return type;
    }


}
