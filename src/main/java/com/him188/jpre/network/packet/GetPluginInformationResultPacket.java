package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Pack;
import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class GetPluginInformationResultPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.GET_PLUGIN_INFORMATION_RESULT;

	private String name;
	private String author;
	private String version;
	private String main;
	private int api;
	private String description;

	public GetPluginInformationResultPacket(String name, String author, String version, String main, int api, String description){
		this.name = name;
		this.author = author;
		this.version = version;
		this.main = main;
		this.api = api;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public int getApi() {
		return api;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}

	public String getMain() {
		return main;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public byte[] encode() {
		return new Pack().putString(name).putString(main).putString(version).putInt(api).putString(author).putString(description).getData();
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
