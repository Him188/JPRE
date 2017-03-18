package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class DisablePluginPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.DISABLE_PLUGIN;

	private String name;

	public String getName() {
		return name;
	}

	@Override
	public byte[] encode() {
		return new byte[0];
	}

	@Override
	public void decode(Unpack unpack) {
		this.name = unpack.getString();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
