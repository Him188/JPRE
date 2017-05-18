package net.mamoe.jpre.event.network;

import net.mamoe.jpre.event.Cancellable;
import net.mamoe.jpre.network.packet.Packet;

/**
 * 服务器数据包接受事件
 *
 * @author Him188
 */
public class DataPacketSendEvent extends NetworkEvent implements Cancellable {
	public DataPacketSendEvent(Packet packet) {
		super(packet);
	}
}
