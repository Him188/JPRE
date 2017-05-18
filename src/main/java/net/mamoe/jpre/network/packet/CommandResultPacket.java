package net.mamoe.jpre.network.packet;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class CommandResultPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.CLIENT_COMMAND_RESULT;

	private long robot;
	private Object result;

	@Override
	public void encode() {

	}

	@Override
	public void decode() {
		if (!setEncoded(false)) {
			return;
		}

		robot = getLong();
		result = getRaw();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}

	public Object getResult() {
		return result;
	}

	public long getRobot() {
		return robot;
	}
}
