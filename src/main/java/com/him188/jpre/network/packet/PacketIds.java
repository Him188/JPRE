package com.him188.jpre.network.packet;

/**
 * @author Him188
 */
public interface PacketIds {
	//receive from client:
	byte CLIENT_RELOAD = 3; //restart the server

	byte CLIENT_PING = 1;
	byte CLIENT_EVENT = 2;

	byte CLIENT_GET_PLUGIN_LIST = 7;

	byte CLIENT_COMMAND_RESULT = 20;


	//send by server:
	byte SERVER_PONG = 9;
	byte SERVER_COMMAND = 10;
	byte SERVER_INVALID_EVENT = 11;
	byte SERVER_EVENT_RESULT = 12;
	byte SERVER_INVALID_ID = 13;
	byte SERVER_LOGIN_RESULT = 14;
	byte SERVER_LOAD_PLUGIN_RESULT = 15;
	byte SERVER_ENABLE_PLUGIN_RESULT = 16;
	byte SERVER_DISABLE_PLUGIN_RESULT = 17;
	byte SERVER_LOAD_PLUGIN_DESCRIPTION_RESULT = 18;
	byte SERVER_GET_PLUGIN_INFORMATION_RESULT = 19;
	byte SERVER_SET_INFORMATION_RESULT = 22;
	byte SERVER_LOG = 23;
	byte SERVER_ACCESS_DENIED = 24;


}
