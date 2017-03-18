package com.him188.jpre.event.jpre;

import com.him188.jpre.event.HandlerList;

import java.net.SocketAddress;

/**
 * 酷 Q 上的 JPRE 插件被关闭
 *
 * @author Him188
 */
public class JPREDisableEvent extends JPREEvent {
	private static final HandlerList handlers = new HandlerList();

	//酷Q的IP. 现在这个参数是唯一的. 以后可能会支持多酷Q同时连接一个JPRE
	public final SocketAddress address;

	public JPREDisableEvent(SocketAddress address) {
		this.address = address;
	}

	public SocketAddress getAddress() {
		return address;
	}

	public static HandlerList getHandlers() {
		return handlers;
	}
}
