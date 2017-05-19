package net.mamoe.jpre.network.packet;

import net.mamoe.jpre.CommandId;
import net.mamoe.jpre.RobotQQ;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class ServerCommandPacket extends Packet {
    public static final byte NETWORK_ID = Protocol.SERVER_COMMAND;

    private final byte id; // 操作 ID, 用于识别是哪一次API调用的返回值
    private final RobotQQ robot;
    private final Object[] args;
    private final CommandId commandId;

    public Object[] getArgs() {
        return args;
    }

    public CommandId getCommandId() {
        return commandId;
    }

    public byte getId() {
        return id;
    }

    public ServerCommandPacket(byte id, RobotQQ robot, CommandId commandId, Object[] args) {
        this.id = id;
        this.robot = robot;
        this.args = args;
        this.commandId = commandId;
    }

    @Override
    public void encode() {
        if (setEncoded(true)) {
            return;
        }

        clear();

        putLong(robot.getQQNumber());
        putByte(commandId.getId());
        putInt(args.length);
        putRawWithType(args);
    }

    @Override
    public void decode() {

    }

    @Override
    public byte getNetworkId() {
        return NETWORK_ID;
    }
}
