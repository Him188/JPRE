package com.him188.jpre.network.packet;

import com.him188.jpre.CommandId;
import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public class CommandPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.SERVER_COMMAND;

	private final RobotQQ robot;
	private final Object[] args;
	private final CommandId id;

	public Object[] getArgs() {
		return args;
	}

	public CommandId getId() {
		return id;
	}

	public CommandPacket(RobotQQ robot, CommandId commandId, Object[] args) {
		this.robot = robot;
		this.args = args;
		id = commandId;
	}

	@Override
	public void encode() {
		if (setEncoded(true)) {
			return;
		}

		clear();

		putLong(robot.getQQNumber());
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
