package com.him188.jpre.network.packet;

import com.him188.jpre.CommandId;

/**
 * @author Him188
 */
public class CommandPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.SERVER_COMMAND;

	private final Object[] args;
	private final CommandId id;

	public Object[] getArgs() {
		return args;
	}

	public CommandId getId() {
		return id;
	}

	public CommandPacket(CommandId commandId, Object[] args) {
		this.args = args;
		id = commandId;
	}

	@Override
	public void encode() {
		if (setEncoded(true)) {
			return;
		}

		clear();

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
