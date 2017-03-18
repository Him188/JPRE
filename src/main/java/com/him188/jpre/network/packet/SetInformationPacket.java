package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class SetInformationPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.SET_INFORMATION;

	private int api;
	private int authCode;

	public int getApi() {
		return api;
	}

	public int getAuthCode() {
		return authCode;
	}

	@Override
	public byte[] encode() {
		return new byte[0];
	}

	@Override
	public void decode(Unpack unpack) {
		this.api = unpack.getInt();
		this.authCode = unpack.getInt();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
