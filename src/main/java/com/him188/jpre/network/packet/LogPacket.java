package com.him188.jpre.network.packet;




/**
 * @author Him188
 */
public class LogPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.LOG;

	private String log;

	public LogPacket(String log){
		this.log = log;
	}

	public String getLog() {
		return log;
	}

	@Override
	public void encode() {
		return new Pack().putString(log).getData();
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
