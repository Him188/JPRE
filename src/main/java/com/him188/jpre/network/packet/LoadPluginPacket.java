package com.him188.jpre.network.packet;



/**
 * @author Him188
 */
public class LoadPluginPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.LOAD_PLUGIN;

	private String name;

	public String getName() {
		return name;
	}

	@Override
	public void encode() {
	}

	@Override
	public void decode() {
		this.name = getString();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
