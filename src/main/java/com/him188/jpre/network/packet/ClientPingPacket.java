package com.him188.jpre.network.packet;

/**
 * @author Him188
 */
public class ClientPingPacket extends Packet{
	public static final byte NETWORK_ID = PacketIds.CLIENT_PING;

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
