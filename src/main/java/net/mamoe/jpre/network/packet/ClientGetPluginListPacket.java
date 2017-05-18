package net.mamoe.jpre.network.packet;


import java.util.List;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class ClientGetPluginListPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.CLIENT_GET_PLUGIN_LIST;

	private List<String> result;

	public void setResult(List<String> result) {
		this.result = result;
	}

	public List<String> getResult() {
		return result;
	}

	@Override
	public void encode() {
		if (setEncoded(true)) {
			return;
		}

		clear();
		putList(getResult(), String.class);
	}

	@Override
	public void decode() {
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
