package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Pack;
import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class EventResultPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.EVENT_RESULT;

	public boolean cancelled;

	public EventResultPacket() {
		this(false);
	}

	public EventResultPacket(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public byte[] encode() {
		return new Pack().putBoolean(cancelled).getData();
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
