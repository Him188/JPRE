package net.mamoe.jpre.network.packet;

import net.mamoe.jpre.CommandId;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.Utils;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class ServerCommandPacket extends Packet {
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

    public ServerCommandPacket(RobotQQ robot, CommandId commandId, Object[] args) {
        this.robot = robot;
        this.args = Utils.convertLongToString(args);
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
