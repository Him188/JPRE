package net.mamoe.jpre.network.packet;

import net.mamoe.jpre.CommandId;

/**
 * @author Him188 @ JPRE Project */
public class ServerStaticCommandPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.SERVER_STATIC_COMMAND;

	private final byte id;
	private final Object[] args;
	private final CommandId commandId;

	public Object[] getArgs() {
		return args;
	}

	public CommandId getCommandId() {
		return commandId;
	}

	public ServerStaticCommandPacket(byte id, CommandId commandId, Object[] args) {
		this.id = id;
		this.args = args;
		this.commandId = commandId;
	}

	@Override
	public void encode() {
		if (setEncoded(true)) {
			return;
		}

		clear();

		putByte(id);
		putByte(commandId.getId());
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
