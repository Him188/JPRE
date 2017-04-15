package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class CommandResultPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.COMMAND_RESULT;

	public Object result;

	public Object getResult() {
		return result;
	}

	public CommandResultPacket(){

	}

	@Override
	public byte[] encode() {
		return new byte[0];
	}

	@Override
	public void decode() {
		switch ((int) unpack.getByte()){
			case 0:
				result = unpack.getInt();
				break;
			case 1:
				result = unpack.getByte();
				break;
			case 2:
				result = unpack.getLong();
				break;
			case 3:
				result = unpack.getString();
				break;
			case 4:
				result = unpack.getShort();
				break;
			case 5:
				result = unpack.getBoolean();
				break;
		}
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
