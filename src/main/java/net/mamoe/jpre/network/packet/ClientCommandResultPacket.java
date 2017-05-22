package net.mamoe.jpre.network.packet;

/**
 * @author Him188 @ JPRE Project
 */
public class ClientCommandResultPacket extends Packet {
    public static final byte NETWORK_ID = Protocol.CLIENT_COMMAND_RESULT;

    private byte id; // 操作 ID, 用于识别是哪一次API调用的返回值
    private long robot;
    private Object result;

    @Override
    public void encode() {

    }

    @Override
    public void decode() {
        if (!setEncoded(false)) {
            return;
        }

        id = getByte();
        robot = getLong();
        result = getRaw();
    }

    @Override
    public byte getNetworkId() {
        return NETWORK_ID;
    }

    public Object getResult() {
        return result;
    }

    public long getRobot() {
        return robot;
    }

    public byte getId() {
        return id;
    }
}
