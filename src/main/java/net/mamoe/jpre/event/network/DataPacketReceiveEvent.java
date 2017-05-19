package net.mamoe.jpre.event.network;

import net.mamoe.jpre.event.Cancellable;
import net.mamoe.jpre.network.packet.Packet;

/**
 * 服务器数据包接受事件
 *
 * @author Him188 @ JPRE Project */
public class DataPacketReceiveEvent extends NetworkEvent implements Cancellable {
	public DataPacketReceiveEvent(Packet packet) {
		super(packet);
	}
}
