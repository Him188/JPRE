package com.him188.jpre.binary;

import java.util.ArrayList;
import java.util.List;

import static com.him188.jpre.binary.Binary.toBytes;

/**
 * @author Him188
 */
public class Pack {
	private List<Byte> data;

	public Pack(Unpack unpack) {
		this(unpack.getData());
	}

	public Pack() {
		this(new byte[]{});
	}

	public Pack(byte[] data) {
		setData(data);
	}

	public Pack setData(byte[] data) {
		this.data = new ArrayList<Byte>(1024) {
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
		this.data.add(value);
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

	public Pack putBytes(Byte[] value) {
		for (Byte aValue : value) {
			if (aValue == null) {
				continue;
			}
			putByte(aValue);
		}
		return this;
	}

	public Pack putRaw(Object... values) {
		for (Object value : values) {
			if (value.getClass().equals(int.class) || value.getClass().equals(Integer.class)) {
				putInt((Integer) value);
			} else if (value.getClass().equals(byte.class) || value.getClass().equals(Byte.class)) {
				putByte((Byte) value);
			} else if (value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
				putLong((Long) value);
			} else if (value.getClass().equals(String.class)) {
				putString((String) value);
			} else if (value.getClass().equals(boolean.class) || value.getClass().equals(Boolean.class)) {
				putBoolean((Boolean) value);
			} else if (value.getClass().equals(double.class) || value.getClass().equals(Double.class)) {
				putDouble((Double) value);
			} else if (value.getClass().equals(short.class) || value.getClass().equals(Short.class)) {
				putShort((Short) value);
			} else if (value.getClass().equals(float.class) || value.getClass().equals(Float.class)) {
				putFloat((Float) value);
			} else if (value.getClass().equals(byte[].class)) {
				putBytes((byte[]) value);
			} else if (value.getClass().equals(Byte[].class)){
				putBytes((Byte[]) value);
			}
		}
		return this;
	}

}
