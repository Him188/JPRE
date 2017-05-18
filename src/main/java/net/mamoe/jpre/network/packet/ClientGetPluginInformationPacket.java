package net.mamoe.jpre.network.packet;


/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class ClientGetPluginInformationPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.CLIENT_GET_PLUGIN_INFORMATION;

	private String name;

	public String getName() {
		return name;
	}

	@Override
	public void encode() {
	}

	@Override
	public void decode() {
		if (!setEncoded(false)) {
			return;
		}

		this.name = this.getString();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
