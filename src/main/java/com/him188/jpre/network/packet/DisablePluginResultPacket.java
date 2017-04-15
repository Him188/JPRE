package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Pack;
import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class DisablePluginResultPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.DISABLE_PLUGIN_RESULT;

	private boolean succeed;

	public DisablePluginResultPacket(boolean succeed){
		this.succeed = succeed;
	}

	@Override
	public byte[] encode() {
		return new Pack().putBoolean(succeed).getData();
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
