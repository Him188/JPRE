package net.mamoe.jpre.network.packet;

/**
 * @author Him188 @ JPRE Project
 */
public interface Protocol {
    byte[] SIGNATURE = {127, 127, 127, 127};

    //receive from client:
    // TODO: 2017/4/21  restart the server
    byte CLIENT_RELOAD = 1;

    byte CLIENT_PING = 2;
    byte CLIENT_EVENT = 3;

    byte CLIENT_GET_PLUGIN_LIST = 4;
    byte CLIENT_GET_PLUGIN_INFORMATION = 5;

    byte CLIENT_COMMAND_RESULT = 6;
    byte CLIENT_STATIC_COMMAND_RESULT = 14;


    //send by server:
    byte SERVER_PONG = 7;
    byte SERVER_COMMAND = 8;
    byte SERVER_STATIC_COMMAND = 14;
    byte SERVER_INVALID_EVENT = 9;
    byte SERVER_EVENT_RESULT = 10;
    byte SERVER_INVALID_ID = 11;
    byte SERVER_LOG = 12;
    byte SERVER_GET_PLUGIN_INFORMATION_RESULT = 13;

    //max id: 14
}
