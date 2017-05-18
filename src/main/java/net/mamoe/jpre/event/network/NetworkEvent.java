package net.mamoe.jpre.event.network;

import net.mamoe.jpre.event.Event;
import net.mamoe.jpre.network.packet.Packet;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public abstract class NetworkEvent extends Event {
    private final Packet packet;

    public NetworkEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}