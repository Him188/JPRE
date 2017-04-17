package com.him188.jpre.network.packet;




/**
 * @author Him188
 */
public class LoadPluginResultPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.LOAD_PLUGIN_RESULT;

	public boolean succeed;

	public LoadPluginResultPacket(boolean succeed){
		this.succeed = succeed;
	}

	@Override
	public void encode() {
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
