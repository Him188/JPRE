package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class GetPluginInformationPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.GET_PLUGIN_INFORMATION;

	private String name;

	public String getName() {
		return name;
	}

	@Override
	public byte[] encode() {
		return new byte[0];
	}

	@Override
	public void decode() {
		this.name = unpack.getString();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
