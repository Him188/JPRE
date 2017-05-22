package net.mamoe.jpre.network.packet;



/**
 * @author Him188 @ JPRE Project
 */
public class ServerPongPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.SERVER_PONG;

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
