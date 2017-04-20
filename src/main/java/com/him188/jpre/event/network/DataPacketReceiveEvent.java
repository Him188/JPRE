package com.him188.jpre.event.network;

import com.him188.jpre.network.packet.Packet;

/**
 * 服务器数据包接受事件
 *
 * @author Him188
 */
public class DataPacketReceiveEvent extends NetworkEvent {
	private final Packet packet;

	public DataPacketReceiveEvent(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}
}
