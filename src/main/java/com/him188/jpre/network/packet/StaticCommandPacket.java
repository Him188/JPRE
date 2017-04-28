package com.him188.jpre.network.packet;

import com.him188.jpre.CommandId;

/**
 * @author Him188
 */
public class StaticCommandPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.SERVER_STATIC_COMMAND;

	private final Object[] args;
	private final CommandId id;

	public Object[] getArgs() {
		return args;
	}

	public CommandId getId() {
		return id;
	}

	public StaticCommandPacket(CommandId commandId, Object[] args) {
		this.args = args;
		id = commandId;
	}

	@Override
	public void encode() {
		if (setEncoded(true)) {
			return;
		}

		clear();

		putLong(0L);
		putByte(id.getId());
		putRaw(args);
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return 0;
	}
}
