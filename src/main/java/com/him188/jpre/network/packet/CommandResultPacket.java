package com.him188.jpre.network.packet;

/**
 * @author Him188
 */
public class CommandResultPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.CLIENT_COMMAND_RESULT;

	private Object result;

	@Override
	public void encode() {

	}

	@Override
	public void decode() {
		result = getRaw();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}

	public Object getResult() {
		return result;
	}
}
