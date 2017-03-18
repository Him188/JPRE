package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Pack;
import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class EventPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.EVENT_RESULT;

	public byte cancelled;

	public EventPacket() {
		this(false);
	}

	public EventPacket(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public byte[] encode() {
		return new Pack().putBoolean(cancelled).getData();
	}

	@Override
	public void decode(Unpack unpack) {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
