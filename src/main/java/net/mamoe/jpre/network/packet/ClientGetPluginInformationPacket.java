package net.mamoe.jpre.network.packet;


/**
 * @author Him188 @ JPRE Project
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

		this.name = this.getStringDecoded();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
