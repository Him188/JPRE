package com.him188.jpre.network.packet;

/**
 * @author Him188
 */
public class LogPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.SERVER_LOG;

	private String log;

	public LogPacket(String log){
		this.log = log;
	}

	public String getLog() {
		return log;
	}

	@Override
	public void encode() {
		putString(log);
		// TODO: 2017/4/21 转换字节发送
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
