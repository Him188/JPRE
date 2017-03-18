package com.him188.jpre.network.packet;

/**
 * @author Him188
 */
public interface PacketIds {
	//receive from client:
	byte PING = 0x01;
	byte EVENT = 0x04;
	byte LOGIN = 0x08;

	//send by server:
	byte PONG = 0x02;
	byte COMMAND = 0x03;
	byte INVALID_EVENT = 0x05;
	byte EVENT_REPLAY = 0x06;
	byte INVALID_ID = 0x07;
	byte LOGIN_RESULT = 0x09;
}
