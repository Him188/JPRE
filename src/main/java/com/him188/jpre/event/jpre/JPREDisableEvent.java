package com.him188.jpre.event.jpre;

import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.network.ConnectedClient;


// TODO: 2017/4/10 包名更改为 event.mpq, 添加mpq插件启动/关闭事件, mpq重启事件
/**
 * MPQ 上的 JPRE 插件被关闭
 *
 * @author Him188
 */
public class JPREDisableEvent extends JPREEvent {
	private static final HandlerList handlers = new HandlerList();

	//IP. 现在这个参数是唯一的. 以后可能会支持多MPQ同时连接一个JPRE
	public final ConnectedClient client;

	public JPREDisableEvent(ConnectedClient address) {
		this.client = address;
	}

	public ConnectedClient getClient() {
		return client;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}

	public static int getEventType() {
		return EventTypes.JPRE_DISABLE;
	}

}
