package com.him188.jpre.binary;

import java.util.Arrays;

import static com.him188.jpre.binary.Binary.*;

/**
 * 数据包解码器,
 *
 * @author Him188
 */
public class Unpack {
	protected byte[] data;
	protected int location = 0;

	public Unpack(Pack pack) {
		this(pack.getData());
	}

	public Unpack() {
		this(new byte[]{});
	}

	public Unpack(byte[] data) {
		setData(data);
	}

	public void setLocation(int location) {
		this.location = location;
	}

	protected byte[] getCenter(int location, int length) {
		if (length == 0) {
			return new byte[0];
		}
		byte[] result = new byte[length];
		try {
			System.arraycopy(data, location, result, 0, length);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[Error] Unpack getCenter 数组越界, 数据: " + Arrays.toString(data));
			System.out.println("                                  当前位置: " + location);
		}

		return result;
	}

	public void setData(byte[] data) {
		this.data = data;
		location = 0;
	}

	public byte[] getData() {
		return data;
	}

	public void clear() {
		this.data = new byte[]{};
	}

	public byte[] getAll() {
		return getCenter(location, getLength());
	}

	public byte[] getBytes(int length) {
		byte[] result = getCenter(location, length);


		location += length;
		return result;
	}

	public byte[] getLast() {
		return getBytes(getLength());
	}

	public int getLength() {
		return data.length - location;
	}

	public byte getByte() {
		return getBytes(1)[0];
	}

	public int getInt() {
		return toInt(getBytes(4));
	}

	public long getLong() {
		return toLong(getBytes(8));
	}

	public short getShort() {
		return toShort(getBytes(2));
	}

	public String getString() {
		return new String(getBytes(getInt()));
	}

	public boolean getBoolean() {
		return (int) getByte() == 1;
	}

	public byte[] getToken() {
		return getBytes(getShort());
	}
}