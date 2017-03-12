package net.coding.lamgc.coolq.jpre.event.group;

import net.coding.lamgc.coolq.jpre.event.EventTypes;
import net.coding.lamgc.coolq.jpre.event.HandlerList;

/**
 * 群成员减少事件
 *
 * @author Him188
 */
public class GroupMemberDecreaseEvent extends GroupEvent {
	public static final int TYPE_UNKNOWN = 0; //未知 (多半由插件创建本事件时传参 type 不正确导致)
	public static final int TYPE_QUIT = 1; //群成员退出
	public static final int TYPE_KICK = 2; //群成员被踢出
	public static final int TYPE_SELF_KICK = 3; //自己(登录号)被踢
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final int time;
	public final long group;
	public final long QQ; //被操作人
	public final long operator; //操作人 (仅子类型 TYPE_KICK 或 TYPE_SELF_KICK 时非 0)

	public GroupMemberDecreaseEvent(int type, int time, long group, long operator, long QQ) {
		this.type = (type >= 1 && type <= 3 ? type : 0);
		this.time = time;
		this.group = group;
		this.operator = operator;
		this.QQ = QQ;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.GROUP_MEMBER_DECREASE;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public int getType() {
		return type;
	}

	public int getTime() {
		return time;
	}

	public long getGroup() {
		return group;
	}

	public long getOperator() {
		return operator;
	}

	public long getQQ() {
		return QQ;
	}
}
