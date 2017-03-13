package com.him188.jpre.event.action.replay;

import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.message.GroupMessageEvent;
import com.him188.jpre.infomation.Anonymous;

/**
 * 回复群消息事件
 *
 * @author Him188
 */
public class ReplayGroupMessageEvent extends ReplayMessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_GROUP = 1;
	private static final HandlerList handlers = new HandlerList();
	public final Anonymous fromAnonymous; //非空时消息来自匿名者
	public final int time;
	public final int type;
	public final long QQ;
	public final long group;
	public final int font; //接受消息的字体
	public final String message; //收到的消息
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public ReplayGroupMessageEvent(GroupMessageEvent event) {
		this(event.type, event.time, event.group, event.QQ, event.fromAnonymous, event.message, event.font);
	}

	public ReplayGroupMessageEvent(int type, int time, long group, long QQ, Anonymous fromAnonymous, String message, int font) {
		this.type = type == TYPE_GROUP ? type : TYPE_UNKNOWN;
		this.time = time;
		this.QQ = QQ;
		this.group = group;
		this.fromAnonymous = fromAnonymous;
		this.message = message;
		this.font = font;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.PLUGIN_ACTION_REPLAY_GROUP;
	}

	public Anonymous getFromAnonymous() {
		return fromAnonymous;
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

	public int getFont() {
		return font;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getMessage() {
		return message;
	}

	public long getQQ() {
		return QQ;
	}

	public long getGroup() {
		return group;
	}
}
