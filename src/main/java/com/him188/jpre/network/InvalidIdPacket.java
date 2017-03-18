package com.him188.jpre.network;

import com.him188.jpre.binary.Unpack;
import com.him188.jpre.network.packet.Packet;
import com.him188.jpre.network.packet.PacketIds;

/**
 * @author Him188
 */
public class InvalidIdPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.INVALID_ID;

	@Override
	public byte[] encode() {
		return new byte[0];
	}

	@Override
	public void decode(Unpack unpack) {

	}
}
