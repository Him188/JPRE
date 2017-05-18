package net.mamoe.jpre.network.packet;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class EventResultPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.SERVER_EVENT_RESULT;

	// TODO: 2017/4/28 int 返回值 
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
		putRawWithType(cancelled);
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
