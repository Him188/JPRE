package com.him188.jpre.event.action.replay;

import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.message.PrivateMessageEvent;

/**
 * 回复私聊消息事件
 *
 * @author Him188
 */
public class ReplayPrivateMessageEvent extends ReplayMessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_FRIEND = 11; //来自好友
	public static final int TYPE_ONLINE = 1; //来自在线状态
	public static final int TYPE_GROUP = 2; //来自群
	public static final int TYPE_DISCUSS = 3; //来自讨论组
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final long QQ;
	public final int time;
	public final int font; //收到消息的字体
	public String message;
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public ReplayPrivateMessageEvent(PrivateMessageEvent event) {
		this(event.type, event.time, event.QQ, event.message, event.font);
	}

	public ReplayPrivateMessageEvent(int type, int time, long QQ, String message, int font) {
		this.type = type == TYPE_FRIEND || type == TYPE_GROUP || type == TYPE_DISCUSS || type == TYPE_ONLINE ? type : TYPE_UNKNOWN;
		this.QQ = QQ;
		this.time = time;
		this.message = message;
		this.font = font;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.PLUGIN_ACTION_REPLAY_PRIVATE;
	}

	@Override
	public int getTime() {
		return time;
	}

	public int getFont() {
		return font;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public int getType() {
		return type;
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

	public long getQQ() {
		return QQ;
	}
}


