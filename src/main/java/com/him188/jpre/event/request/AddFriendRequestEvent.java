package com.him188.jpre.event.request;

import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.EventTypes;

/**
 * 添加好友请求事件
 *
 * @author Him188
 */
public class AddFriendRequestEvent extends RequestEvent {
	public static final int TYPE_UNKNOWN = 0; //未知 (多半由插件创建本事件时传参 type 不正确导致)
	public static final int TYPE_ADD = 1;
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long QQ;
	public final int time;
	public final String message;
	public final String responseFlag;

	public boolean accept = false;
	public String nickIfAccept = "";

	public AddFriendRequestEvent(int type, int time, long QQ, String message, String responseFlag) {
		this.type = type == 1 ? type : TYPE_UNKNOWN;
		this.time = time;
		this.QQ = QQ;
		this.message = message;
		this.responseFlag = responseFlag;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.REQUEST_FRIEND_ADD;
	}

	public String getNickIfAccept() {
		return nickIfAccept;
	}

	public void setNickIfAccept(String nickIfAccept) {
		this.nickIfAccept = nickIfAccept;
	}

	public boolean isAccept() {
		return accept;
	}

	@SuppressWarnings("SameParameterValue")
	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
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

	public String getMessage() {
		return message;
	}

	public String getResponseFlag() {
		return responseFlag;
	}
}
