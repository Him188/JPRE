package com.him188.jpre.event.network;

import com.him188.jpre.event.Event;
import com.him188.jpre.network.ConnectedClient;
import com.him188.jpre.network.packet.Packet;

/**
 * @author Him188
 */
abstract public class NetworkEvent extends Event {

	abstract public Packet getPacket();

	abstract public ConnectedClient getClient();
}
