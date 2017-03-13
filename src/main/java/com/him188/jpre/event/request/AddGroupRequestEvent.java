package com.him188.jpre.event.request;

import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.EventTypes;

/**
 * 添加群请求事件
 *
 * @author Him188
 */
public class AddGroupRequestEvent extends RequestEvent {
	public static final int TYPE_UNKNOWN = 0; //未知 (多半由插件创建本事件时传参 type 不正确导致)
	public static final int TYPE_JOIN = 1;//他人申请入群
	public static final int TYPE_INVITE = 2;//自己(登录号)受邀请入群
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long group;
	public final long QQ;
	public final int time;
	public final String message;
	public final String responseFlag;

	public boolean accept = false;
	public String reasonIfRefused = "";

	public AddGroupRequestEvent(int type, int time, long group, long QQ, String message, String responseFlag) {
		this.type = type == 1 || type == 2 ? type : TYPE_UNKNOWN;
		this.time = time;
		this.group = group;
		this.QQ = QQ;
		this.message = message;
		this.responseFlag = responseFlag;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.REQUEST_GROUP_ADD;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public String getReasonIfRefused() {
		return reasonIfRefused;
	}

	public void setReasonIfRefused(String reasonIfRefused) {
		this.reasonIfRefused = reasonIfRefused;
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

	public String getMessage() {
		return message;
	}

	public String getResponseFlag() {
		return responseFlag;
	}
}
