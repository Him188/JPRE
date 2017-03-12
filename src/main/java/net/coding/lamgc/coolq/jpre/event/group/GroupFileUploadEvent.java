package net.coding.lamgc.coolq.jpre.event.group;

import net.coding.lamgc.coolq.jpre.Utils;
import net.coding.lamgc.coolq.jpre.event.EventTypes;
import net.coding.lamgc.coolq.jpre.event.HandlerList;
import net.coding.lamgc.coolq.jpre.infomation.File;

/**
 * 群文件上传事件
 *
 * @author Him188
 */
public class GroupFileUploadEvent extends GroupEvent {
	public static final int TYPE_UNKNOWN = 0; //未知 (多半由插件创建本事件时传参 type 不正确导致)
	public static final int TYPE_UPLOAD = 1;
	private static final HandlerList handlers = new HandlerList();
	public final int type;
	public final int time;
	public final long group;
	public final long QQ;
	public final File file; //文件信息

	public GroupFileUploadEvent(int type, int time, long group, long QQ, String file) {
		this.type = type;
		this.time = time;
		this.group = group;
		this.QQ = QQ;

		byte[] code = Utils.base64decode(file);
		if (code.length == 0) {
			this.file = null;
		} else {
			this.file = new File(code);
		}
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.GROUP_UPLOAD;
	}

	public boolean isValid() {
		return this.type != TYPE_UNKNOWN;
	}

	public int getTime() {
		return time;
	}

	public int getType() {
		return type;
	}

	public long getGroup() {
		return group;
	}

	public long getQQ() {
		return QQ;
	}

	public File getFile() {
		return file;
	}
}
