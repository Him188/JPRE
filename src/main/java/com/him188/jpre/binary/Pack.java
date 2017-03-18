package com.him188.jpre.binary;

import java.util.ArrayList;
import java.util.List;

import static com.him188.jpre.binary.Binary.toBytes;

/**
 * @author Him188
 */
public class Pack {
	private List<Byte> data;
	private int position;

	public Pack(Unpack unpack) {
		this(unpack.getData());
	}

	public Pack() {
		this(new byte[]{});
	}

	public Pack(byte[] data) {
		this.position = 0;
		setData(data);
	}

	public void setData(byte[] data) {
		this.data = new ArrayList<Byte>() {
			{
				for (byte datum : data) {
					add(datum);
				}
			}
		};
	}

	public byte[] getData() {
		byte[] result = new byte[data.size()];
		for (int i = 0; i < data.size(); i++) {
			result[i] = data.get(i);
		}
		return result;
	}

	public void clear() {
		this.data.clear();
	}

	public void putInt(int value) {
		putBytes(toBytes(value));
	}

	public void putLong(long value) {
		putBytes(toBytes(value));
	}

	public void putShort(short value) {
		putBytes(toBytes(value));
	}

	public void putBoolean(boolean value) {
		putBytes(toBytes(value));
	}

	public void putFloat(float value) {
		putBytes(toBytes(value));
	}

	public void putDouble(double value) {
		putBytes(toBytes(value));
	}

	public void putByte(byte value) {
		this.data.set(position++, value);
	}

	public void putString(String value) {
		putInt(value.length());
		putBytes(value.getBytes());
	}

	public void putBytes(byte[] value) {
		for (byte aValue : value) {
			putByte(aValue);
		}
	}
}
