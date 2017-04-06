package com.him188.jpre.event.message;

import com.him188.jpre.event.HandlerList;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.infomation.Font;

/**
 * 讨论组消息接收事件
 *
 * @author Him188
 */
public class DiscussMessageEvent extends MessageEvent {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_DISCUSS = 1;
	private static final HandlerList handlers = new HandlerList();
	public final long QQ;
	public final int time;
	public final int type;
	public final long discuss;
	public final Font font;
	public String message; //收到的消息
	public String repeat = ""; //回复信息, null 或 空字符串 为不回复

	public DiscussMessageEvent(int type, int time, long discuss, long QQ, String message, byte[] font) {
		this.type = type == TYPE_DISCUSS ? type : TYPE_UNKNOWN;
		this.time = time;
		this.QQ = QQ;
		this.discuss = discuss;
		this.message = message;
		this.font = new Font(font);
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.DISCUSS_MESSAGE;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public long getDiscuss() {
		return discuss;
	}

	public int getTime() {
		return time;
	}

	public int getType() {
		return type;
	}

	public Font getFont() {
		return font;
	}

	public long getQQ() {
		return QQ;
	}

	public String getMessage() {
		return message;
	}

	/*public void setMessage(String message) {
		this.message = message;
	}*/

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
}
