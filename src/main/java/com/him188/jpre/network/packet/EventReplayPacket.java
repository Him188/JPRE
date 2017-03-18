package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Pack;
import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class EventReplayPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.EVENT_REPLAY;

	public boolean cancelled;

	public EventReplayPacket() {
		this(false);
	}

	public EventReplayPacket(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public byte[] encode() {
		return new Pack().putBoolean(cancelled).getData();
	}

	@Override
	public void decode(Unpack unpack) {

	}
}
