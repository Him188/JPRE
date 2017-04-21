package com.him188.jpre.network.packet;



/**
 * @author Him188
 */
public class InvalidIdPacket extends Packet {
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
