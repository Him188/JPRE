package com.him188.jpre.event.group;

import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.EventTypes;

/**
 * 群管理员变动事件
 *
 * @author Him188
 */
public class GroupAdminChangeEvent extends GroupEvent {
	public static final int TYPE_UNKNOWN = 0; //未知 (多半由插件创建本事件时传参 type 不正确导致)
	public static final int TYPE_REMOVE = 1; // 取消
	public static final int TYPE_ADD = 2; // 添加
	private static final HandlerList handlers = new HandlerList();
	public final long group;
	public final int type;
	public final long QQ;
	public final int time;

	public GroupAdminChangeEvent(int type, int time, long group, long QQ) {
		this.type = type == 1 || type == 2 ? type : TYPE_UNKNOWN;
		this.time = time;
		this.group = group;
		this.QQ = QQ;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.GROUP_ADMIN;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public long getGroup() {
		return group;
	}

	public int getType() {
		return type;
	}

	public long getQQ() {
		return QQ;
	}

	public int getTime() {
		return time;
	}
}
