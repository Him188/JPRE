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

	public Pack setData(byte[] data) {
		this.data = new ArrayList<Byte>() {
			{
				for (byte datum : data) {
					add(datum);
				}
			}
		};
		return this;
	}

	public byte[] getData() {
		byte[] result = new byte[data.size()];
		for (int i = 0; i < data.size(); i++) {
			result[i] = data.get(i);
		}
		return result;
	}

	public Pack clear() {
		this.data.clear();
		return this;
	}

	public Pack putInt(int value) {
		putBytes(toBytes(value));
		return this;
	}

	public Pack putLong(long value) {
		putBytes(toBytes(value));
		return this;
	}

	public Pack putShort(short value) {
		putBytes(toBytes(value));
		return this;
	}

	public Pack putBoolean(boolean value) {
		putBytes(toBytes(value));
		return this;
	}

	public Pack putFloat(float value) {
		putBytes(toBytes(value));
		return this;
	}

	public Pack putDouble(double value) {
		putBytes(toBytes(value));
		return this;
	}

	public Pack putByte(byte value) {
		this.data.set(position++, value);
		return this;
	}

	public Pack putString(String value) {
		putInt(value.length());
		putBytes(value.getBytes());
		return this;
	}

	public Pack putBytes(byte[] value) {
		for (byte aValue : value) {
			putByte(aValue);
		}
		return this;
	}
}
