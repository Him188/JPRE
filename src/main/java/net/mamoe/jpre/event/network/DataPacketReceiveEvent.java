package net.mamoe.jpre.event.network;

import net.mamoe.jpre.network.packet.Packet;

/**
 * 服务器数据包接受事件
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
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
