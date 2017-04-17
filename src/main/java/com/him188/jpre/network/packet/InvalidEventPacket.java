package com.him188.jpre.network.packet;



/**
 * @author Him188
 */
public class InvalidEventPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.INVALID_EVENT;

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
