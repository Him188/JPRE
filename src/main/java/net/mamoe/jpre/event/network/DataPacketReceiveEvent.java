package net.mamoe.jpre.event.network;

import net.mamoe.jpre.network.packet.Packet;

/**
 * 服务器数据包接受事件
 *
 * @author Him188
 */
public class DataPacketReceiveEvent extends NetworkEvent {
	public DataPacketReceiveEvent(Packet packet) {
		super(packet);
	}
}
