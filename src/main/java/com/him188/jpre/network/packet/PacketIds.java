package com.him188.jpre.network.packet;

/**
 * @author Him188
 */
public interface PacketIds {
	//receive from client:
	byte PING = 1;
	byte EVENT = 2;
	byte LOGIN = 3;
	byte LOAD_PLUGIN = 4;
	byte ENABLE_PLUGIN = 5;
	byte DISABLE_PLUGIN = 6;
	byte LOAD_PLUGIN_DESCRIPTION = 7;
	byte GET_PLUGIN_INFORMATION = 8;
	byte COMMAND_RESULT = 20;
	byte SET_INFORMATION = 21;

	//send by server:
	byte PONG = 9;
	byte COMMAND = 10;
	byte INVALID_EVENT = 11;
	byte EVENT_RESULT = 12;
	byte INVALID_ID = 13;
	byte LOGIN_RESULT = 14;
	byte LOAD_PLUGIN_RESULT = 15;
	byte ENABLE_PLUGIN_RESULT = 16;
	byte DISABLE_PLUGIN_RESULT = 17;
	byte LOAD_PLUGIN_DESCRIPTION_RESULT = 18;
	byte GET_PLUGIN_INFORMATION_RESULT = 19;
	byte SET_INFORMATION_RESULT = 22;
	byte LOG = 23;
}
