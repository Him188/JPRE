package com.him188.jpre.event.network;

import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.HandlerList;
import com.him188.jpre.network.ConnectedClient;
import com.him188.jpre.network.packet.Packet;

/**
 * @author Him188
 */
public class DataPacketReceiveEvent extends NetworkEvent {
	private final Packet packet;
	private final ConnectedClient client;

	private static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlers() {
		return handlers;
	}

	public DataPacketReceiveEvent(Packet packet, ConnectedClient client){
		this.packet = packet;
		this.client = client;
	}

	@Override
	public ConnectedClient getClient() {
		return client;
	}

	@Override
	public Packet getPacket() {
		return packet;
	}

	public static int getEventType() {
		return EventTypes.DATA_PACKET_RECEIVE;
	}
}
