package com.him188.jpre.network.packet;

/**
 * 无效事件(事件 ID 无效)
 *
 * @author Him188
 */
public class InvalidEventPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.SERVER_INVALID_ID;

	@Override
	public void encode() {
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
