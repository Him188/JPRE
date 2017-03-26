package com.him188.jpre.event.message;

import com.him188.jpre.Utils;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.infomation.Anonymous;
import com.him188.jpre.event.EventTypes;

/**
 * 群消息接收事件
 *
 * @author Him188
 */
public class GroupMessageEvent extends MessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_GROUP = 1;
	private static final HandlerList handlers = new HandlerList();
	public final Anonymous fromAnonymous; //非空时消息来自匿名者
	public final int time;
	public final int type;
	public final long QQ;
	public final long group;
	public final int font;
	public String message; //收到的消息
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public GroupMessageEvent(int type, int time, long group, long QQ, String fromAnonymous, String message, int font) {
		this.type = type == TYPE_GROUP ? type : TYPE_UNKNOWN;
		this.time = time;
		this.QQ = QQ;
		this.group = group;

		byte[] code = Utils.base64decode(fromAnonymous);
		if (code.length == 0) {
			this.fromAnonymous = null;
		} else
			this.fromAnonymous = new Anonymous(code);

		this.message = message;
		this.font = font;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.GROUP_MESSAGE;
	}

	public long getGroup() {
		return this.group;
	}

	public int getType() {
		return this.type;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public long getQQ() {
		return this.QQ;
	}

	public Anonymous getFromAnonymous() {
		return this.fromAnonymous;
	}

	public String getMessage() {
		return this.message;
	}

	/*public void setMessage(String message) {
		this.message = message;
	}*/

	public int getTime() {
		return this.time;
	}

	public int getFont() {
		return this.font;
	}

	public String getRepeat() {
		return this.repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
}
