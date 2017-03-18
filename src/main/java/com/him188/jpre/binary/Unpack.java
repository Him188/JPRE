package com.him188.jpre.binary;

import static com.him188.jpre.binary.Binary.*;
/**
 * @author Him188
 */
public class Unpack {
	private byte[] data;
	private int location = 0;

	public Unpack(Pack pack){
		this(pack.getData());
	}

	public Unpack() {
		this(new byte[]{});
	}

	public Unpack(byte[] data) {
		setData(data);
	}

	private static byte[] getCenter(byte[] array, int location, int length) {
		byte[] result = new byte[length];
		System.arraycopy(array, location, result, 0, length);
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
		return getCenter(data, location, getLength());
	}

	public byte[] getBytes(int length) {
		byte[] result = getCenter(data, location, length);
		location += length;
		return result;
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
		return new String(getBytes(getShort()));
	}

	public byte[] getToken() {
		return getBytes(getShort());
	}
}