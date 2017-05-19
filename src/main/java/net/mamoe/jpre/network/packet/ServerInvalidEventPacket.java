package net.mamoe.jpre.network.packet;

/**
 * 无效包(包 ID 无效)
 *
 * @author Him188 @ JPRE Project */
public class ServerInvalidEventPacket extends Packet {
    public static final byte NETWORK_ID = Protocol.SERVER_INVALID_ID;

    @Override
    public void encode() {
    }

    @Override
    public void decode() {

    }

    @Override
    public byte getNetworkId() {
        return NETWORK_ID;
    }
}
