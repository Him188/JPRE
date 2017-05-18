package net.mamoe.jpre.network.packet;

/**
 * 无效事件(事件 ID 无效)
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class ServerInvalidEventPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.SERVER_INVALID_EVENT;

	@Override
	public void encode() {
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
