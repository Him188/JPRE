package net.mamoe.jpre.network;

import net.mamoe.jpre.network.packet.Packet;
import net.mamoe.jpre.network.packet.Protocol;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE
 */
public class ServerInvalidIdPacket extends Packet {
    public static final byte NETWORK_ID = Protocol.SERVER_PACKET_PROCESSING_EXCEPTION;

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
