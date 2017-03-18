package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Pack;
import com.him188.jpre.binary.Unpack;

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
	public byte[] encode() {
		return new Pack().putString(log).getData();
	}

	@Override
	public void decode(Unpack unpack) {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
