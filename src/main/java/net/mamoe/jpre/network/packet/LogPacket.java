package net.mamoe.jpre.network.packet;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class LogPacket extends Packet {
    public static final byte NETWORK_ID = Protocol.SERVER_LOG;

    private String log;

    public LogPacket(String log){
        this.log = log;
    }

    @Override
    public void encode() {
        if (setEncoded(true)) {
            return;
        }

        clear();
        putString(log);
    }

    @Override
    public void decode() {

    }

    @Override
    public byte getNetworkId() {
        return NETWORK_ID;
    }
}
