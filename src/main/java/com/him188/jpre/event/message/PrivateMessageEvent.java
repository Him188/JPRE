package com.him188.jpre.event.message;

import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.EventTypes;

/**
 * 私聊消息接收事件
 *
 * @author Him188
 */
public class PrivateMessageEvent extends MessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_FRIEND = 11; //来自好友
	public static final int TYPE_ONLINE = 1; //来自在线状态
	public static final int TYPE_GROUP = 2; //来自群
	public static final int TYPE_DISCUSS = 3; //来自讨论组
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long QQ;
	public final int font;
	public final int time;
	public String message; //收到的消息
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public PrivateMessageEvent(int type, int time, long QQ, String message, int font) {
		this.type = type == TYPE_FRIEND || type == TYPE_GROUP || type == TYPE_DISCUSS || type == TYPE_ONLINE ? type : TYPE_UNKNOWN;
		this.QQ = QQ;
		this.message = message;
		this.time = time;
		this.font = font;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.PRIVATE_MESSAGE;
	}

	public int getFont() {
		return font;
	}

	public int getTime() {
		return time;
	}

	public int getType() {
		return type;
	}

	public long getQQ() {
		return QQ;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRepeat() {
		return repeat;
	}

	@SuppressWarnings("SameParameterValue")
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
}
