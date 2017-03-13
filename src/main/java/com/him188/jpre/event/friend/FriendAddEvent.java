package com.him188.jpre.event.friend;

import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.EventTypes;

/**
 * 好友已添加事件
 *
 * @author Him188
 */
public class FriendAddEvent extends FriendEvent {
	public static final int TYPE_UNKNOWN = 0; //未知 (多半由插件创建本事件时传参 type 不正确导致)
	public static final int TYPE_ADD = 1;
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long QQ;
	public final int time;

	public FriendAddEvent(int type, int time, long QQ) {
		this.type = type == 1 ? type : TYPE_UNKNOWN;
		this.time = time;
		this.QQ = QQ;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.FRIEND_ADD;
	}

	public int getType() {
		return type;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public long getQQ() {
		return QQ;
	}

	public int getTime() {
		return time;
	}
}
