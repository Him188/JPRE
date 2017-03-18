package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Unpack;

import java.lang.reflect.Field;

/**
 * @author Him188
 */
abstract public class Packet {
	abstract public byte[] encode();

	abstract public void decode(Unpack unpack);

	public static byte NETWORK_ID = -1;

	public static byte getNetworkId(Packet packet) throws NoSuchFieldException, IllegalAccessException {
		Field field = packet.getClass().getField("NETWORK_ID");
		return (byte) field.get(null);
	}

	// TODO: 2017/3/18  注册包, 类似事件
}
