package com.him188.jpre.network.packet;


import java.util.List;

/**
 * @author Him188
 */
public class GetPluginListPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.CLIENT_GET_PLUGIN_LIST;

	private List<String> names;

	public List<String> getNames() {
		return names;
	}

	@Override
	public void encode() {
	}

	@Override
	public void decode() {
		if (!setEncoded(false)) {
			return;
		}

		this.names = this.getList();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
