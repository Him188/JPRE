package com.him188.jpre.event.action.replay;

import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.message.DiscussMessageEvent;
import com.him188.jpre.event.EventTypes;

/**
 * 回复讨论组消息事件
 *
 * @author Him188
 */
public class ReplayDiscussMessageEvent extends ReplayMessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_DISCUSS = 1;
	private static final HandlerList handlers = new HandlerList();
	public final int time;
	public final int type;
	public final long QQ;
	public final long discuss;
	public final int font;
	public final String message; //收到的消息
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public ReplayDiscussMessageEvent(DiscussMessageEvent event) {
		this(event.type, event.time, event.discuss, event.QQ, event.message, event.font);
		this.repeat = event.getRepeat();
	}

	public ReplayDiscussMessageEvent(int type, int time, long discuss, long QQ, String message, int font) {
		this.type = type == TYPE_DISCUSS ? type : TYPE_UNKNOWN;
		this.time = time;
		this.QQ = QQ;
		this.discuss = discuss;
		this.message = message;
		this.font = font;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.PLUGIN_ACTION_REPLAY_DISCUSS;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public String getMessage() {
		return message;
	}

	public int getType() {
		return type;
	}

	public int getFont() {
		return font;
	}

	public int getTime() {
		return time;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public long getQQ() {
		return QQ;
	}

	public long getDiscuss() {
		return discuss;
	}
}
