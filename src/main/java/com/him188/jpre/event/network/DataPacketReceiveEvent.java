package com.him188.jpre.event.network;

import com.him188.jpre.network.ConnectedClient;
import com.him188.jpre.network.packet.Packet;

/**
 * @author Him188
 */
public class DataPacketReceiveEvent extends NetworkEvent {
	private final Packet packet;
	private final ConnectedClient client;

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
}
