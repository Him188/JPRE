package com.him188.jpre.network.packet;



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
	public void encode() {
	}

	@Override
	public void decode() {
		this.dataFolder = getString();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
