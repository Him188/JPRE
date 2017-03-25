package com.him188.jpre.binary;

import java.util.Arrays;

/**
 * 字节转换. 即将字节数组转换为各种基本数据类型
 *
 * @author Him188
 */
public final class Binary {
	public static byte[] toBytes(int value) {
		byte[] data = new byte[4];
		data[0] = (byte) (value >> 24);
		data[1] = (byte) (value >> 16);
		data[2] = (byte) (value >> 8);
		data[3] = (byte) (value);
		return (data);
	}

	public static byte[] toBytes(boolean value) {
		return (new byte[]{(byte) (value ? 1 : 0)});
	}

	public static byte[] toBytes(float value) {
		return toBytes(Float.floatToIntBits(value));
	}

	public static byte[] toBytes(long value) {
		byte[] data = new byte[8];
		data[0] = (byte) (value >> 56);
		data[1] = (byte) (value >> 48);
		data[2] = (byte) (value >> 40);
		data[3] = (byte) (value >> 32);
		data[4] = (byte) (value >> 24);
		data[5] = (byte) (value >> 16);
		data[6] = (byte) (value >> 8);
		data[7] = (byte) (value);
		return (data);
	}

	public static byte[] toBytes(short value) {
		byte[] data = new byte[2];
		data[2] = (byte) (value >> 8);
		data[3] = (byte) (value);
		return (data);
	}

	public static byte[] toBytes(double value) {
		return toBytes(Double.doubleToLongBits(value));
	}


	public static int toInt(byte[] bytes) {
		return (bytes[0] << 24) + (bytes[1] << 16) + (bytes[2] << 8) + bytes[3];
	}

	public static long toLong(byte[] bytes) {
		return ((long) (bytes[0] & 0xff) << 8) |
				((long) (bytes[1] & 0xff) << 16) |
				((long) (bytes[2] & 0xff) << 24) |
				((long) (bytes[3] & 0xff) << 32) |
				((long) (bytes[4] & 0xff) << 40) |
				((long) (bytes[5] & 0xff) << 48) |
				((long) (bytes[6] & 0xff) << 56);
	}

	public static short toShort(byte[] bytes) {
		return (short) ((bytes[0] << 8) + bytes[1]);
	}

	public static boolean toBoolean(byte[] bytes) {
		return bytes[0] == (byte) 1;
	}

	/**
	 * 反转
	 */
	public static byte[] reverse(byte[] bytes) {
		//return bytes;

		byte[] result = new byte[bytes.length];
		int ii = 0;
		for (int i = bytes.length - 1; i > 0; i--) {
			result[ii++] = bytes[i];
		}
		System.out.println(Arrays.toString(result));
		return result;
	}
}
