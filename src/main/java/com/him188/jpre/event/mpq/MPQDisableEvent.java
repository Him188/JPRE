package com.him188.jpre.event.mpq;

import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.network.MPQClient;


// TODO: 2017/4/10添加mpq插件启动/关闭事件, mpq重启事件
/**
 * MPQ 上的 JPRE 插件被关闭
 *
 * @author Him188
 */
public class MPQDisableEvent extends MPQEvent {
	private static final HandlerList handlers = new HandlerList();

	public MPQDisableEvent(MPQClient address) {
		super(address);
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.JPRE_DISABLE;
	}

}
