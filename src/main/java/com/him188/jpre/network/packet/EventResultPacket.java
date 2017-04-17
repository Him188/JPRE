package com.him188.jpre.network.packet;

/**
 * @author Him188
 */
public class EventResultPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.SERVER_EVENT_RESULT;

	private final boolean cancelled;

	/**
	 * 返回事件是否拦截
	 *
	 * @return 事件是否拦截
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	public EventResultPacket() {
		this(false);
	}

	public EventResultPacket(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public void encode() {
		if (setEncoded(true)) {
			return;
		}

		clear();
		putBoolean(cancelled);
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
