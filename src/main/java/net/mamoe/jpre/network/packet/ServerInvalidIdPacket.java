package net.mamoe.jpre.network.packet;

/**
 * @author Him188 @ JPRE Project
 */
public class ServerInvalidIdPacket extends Packet {
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
