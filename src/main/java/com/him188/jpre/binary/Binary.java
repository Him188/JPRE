package com.him188.jpre.binary;

/**
 * 字节转换. 即将字节数组转换为各种基本数据类型
 *
 * @author Him188
 */
public final class Binary {
	public static int toInt(byte[] bytes) {
		bytes = reverse(bytes);
		return (bytes[0] << 24) + (bytes[1] << 16) + (bytes[2] << 8) + bytes[3];
	}

	public static long toLong(byte[] bytes) {
		bytes = reverse(bytes);
		return ((long) (bytes[0] & 0xff) << 8) |
				((long) (bytes[1] & 0xff) << 16) |
				((long) (bytes[2] & 0xff) << 24) |
				((long) (bytes[3] & 0xff) << 32) |
				((long) (bytes[4] & 0xff) << 40) |
				((long) (bytes[5] & 0xff) << 48) |
				((long) (bytes[6] & 0xff) << 56);
	}

	public static short toShort(byte[] bytes) {
		bytes = reverse(bytes);
		return (short) ((bytes[0] << 8) + bytes[1]);
	}

	/**
	 * 反转
	 */
	public static byte[] reverse(byte[] bytes) {
		byte[] result = new byte[bytes.length];
		int ii = 0;
		for (int i = bytes.length; i > 0; i--) {
			result[ii++] = bytes[i];
		}
		return result;
	}
}
