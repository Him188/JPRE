package com.him188.jpre.network.packet;

/**
 * @author Him188
 */
public class CommandResultPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.CLIENT_COMMAND_RESULT;

	private Object result;

	public Object getResult() {
		return result;
	}

	@Override
	public void encode() {
	}

	@Override
	public void decode() {
		switch ((int) getByte()){
			case 0:
				result = getInt();
				break;
			case 1:
				result = getByte();
				break;
			case 2:
				result = getLong();
				break;
			case 3:
				result = getString();
				break;
			case 4:
				result = getShort();
				break;
			case 5:
				result = getBoolean();
				break;
		}
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
