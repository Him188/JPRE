package net.mamoe.jpre.binary;

/**
 * 字节转换. 字节数组转换为各种基本数据类型
 *
 * @author Him188 @ JPRE Project
 */
@SuppressWarnings("WeakerAccess")
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

	public static byte[] toBytes(long number) {
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();
			temp = temp >> 8;
		}
		return b;
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

	public static double toDouble(byte[] bytes) {
		return Double.longBitsToDouble(
				((long) (bytes[0] & 0xff)) |
				((long) (bytes[1] & 0xff) << 8) |
				((long) (bytes[2] & 0xff) << 16) |
				((long) (bytes[3] & 0xff) << 24) |
				((long) (bytes[4] & 0xff) << 32) |
				((long) (bytes[5] & 0xff) << 40) |
				((long) (bytes[6] & 0xff) << 48) |
				((long) (bytes[7] & 0xff) << 56));
	}

	public static float toFloat(byte[] bytes) {
		return Float.intBitsToFloat((bytes[0] << 24) + (bytes[1] << 16) + (bytes[2] << 8) + bytes[3]);
	}

	public static int toIntAdded(byte[] bytes) {
		return (bytes[0]) + ((bytes[1] > 0 ? bytes[1] + 1 : bytes[1]) << 8) + (bytes[2] << 16) + (bytes[3] << 24); //why??
	}

	public static int toInt(byte[] bytes) {
		return (bytes[0]) + (bytes[1] << 8) + (bytes[2] << 16) + (bytes[3] << 24);
	}

	public static long toLong(byte[] bytes) {
		return ((long) (bytes[0] & 0xff)) |
		       ((long) (bytes[1] & 0xff) << 8) |
		       ((long) (bytes[2] & 0xff) << 16) |
		       ((long) (bytes[3] & 0xff) << 24) |
		       ((long) (bytes[4] & 0xff) << 32) |
		       ((long) (bytes[5] & 0xff) << 40) |
		       ((long) (bytes[6] & 0xff) << 48) |
		       ((long) (bytes[7] & 0xff) << 56);
	}

	public static short toShort(byte[] bytes) {
		return (short) ((bytes[0] << 8) + bytes[1]);
	}

	public static boolean toBoolean(byte[] bytes) {
		return bytes[0] == (byte) 1;
	}

	public static byte[] realReverse(byte[] Array) {
		byte[] new_array = new byte[Array.length];
		for (int i = 0; i < Array.length; i++) {
			new_array[i] = Array[Array.length - i - 1];
		}
		return new_array;
	}
}
