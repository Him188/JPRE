package com.him188.jpre.network.packet;

import com.him188.jpre.CommandId;
import com.him188.jpre.binary.Pack;
import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class CommandPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.COMMAND;

	private Object[] args;
	private CommandId id;

	public CommandPacket (CommandId commandId, Object[] args){
		this.args = args;
		id = commandId;
	}

	@Override
	public byte[] encode() {
		return new Pack().putByte(id.getId()).putRaw(args).getData();
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return 0;
	}
}
