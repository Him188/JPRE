package com.him188.jpre.network.packet;

/**
 * @author Him188
 */
public class ClientPingPacket extends Packet{
	public static final byte NETWORK_ID = PacketIds.PING;

	@Override
	public byte[] encode() {
		return new byte[0];
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
