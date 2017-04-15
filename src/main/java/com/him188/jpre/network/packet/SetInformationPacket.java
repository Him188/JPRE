package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class SetInformationPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.SET_INFORMATION;

	private String dataFolder;

	public String getDataFolder() {
		return dataFolder;
	}

	@Override
	public byte[] encode() {
		return new byte[0];
	}

	@Override
	public void decode() {
		this.dataFolder = unpack.getString();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
