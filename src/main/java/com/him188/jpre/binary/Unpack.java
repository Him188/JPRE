package com.him188.jpre.binary;

import static com.him188.jpre.binary.Binary.*;

/**
 * 数据包解码器,
 *
 * @author Him188
 */
public class Unpack {
	private byte[] data;
	private int location = 0;

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

	private byte[] getCenter(int location, int length) {
		if (length == 0) {
			return new byte[0];
		}
		byte[] result = new byte[length];
		System.arraycopy(data, location, result, 0, length);
		return result;
	}

	public void setData(byte[] data) {
		this.data = data;
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
		return getBytes(length, false);
	}

	public byte[] getBytes(int length, boolean noReversing) {
		byte[] result = getCenter(location, length);


		location += length;
		return noReversing ? result :  reverse(result);
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