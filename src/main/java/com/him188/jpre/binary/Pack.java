package com.him188.jpre.binary;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.him188.jpre.binary.Binary.*;

/**
 * @author Him188
 */
@SuppressWarnings("WeakerAccess")
public class Pack {
	protected byte[] data;
	protected int location;

	public Pack(Pack unpack) {
		this(unpack.getAll());
	}

	public Pack() {
		this(new byte[]{});
	}

	public Pack(byte[] data) {
		setData(data);
	}


	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getAll() {
		return data.clone();
	}

	public void clear() {
		this.data = new byte[0];

	}


	/* Putter */

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
		this.data[location++] = value;

	}

	public void putString(String value) {
		if (value == null) {
			return;
		}

		try {
			putInt(value.getBytes("UTF-8").length);
			putBytes(value.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void putBytes(byte[] value) {
		for (byte aValue : value) {
			putByte(aValue);
		}
	}

	public void putBytes(Byte[] value) {
		for (Byte aValue : value) {
			if (aValue == null) {
				putByte((byte) 0);
				continue;
			}
			putByte(aValue);
		}
	}

	public void putRaw(Object... values) {
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
			} else if (value.getClass().equals(Byte[].class)) {
				putBytes((Byte[]) value);
			} else {
				throw new IllegalArgumentException("[Pack] putRaw: wrong type of values");
			}
		}

	}

	public void putList(List<?> list) {
		list.forEach(this::putRaw);
	}


	/* Getter */
	public byte[] getCenter(int location, int length) {
		if (length == 0) {
			return new byte[0];
		}
		byte[] result = new byte[length];
		try {
			System.arraycopy(data, location, result, 0, length);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int getLastLength() {
		return data.length - location;
	}


	public byte[] getBytes(int length) {
		byte[] result = getCenter(location, length);

		location += length;
		return result;
	}

	public byte[] getLast() {
		return getBytes(getLastLength());
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
		return toBoolean(getBytes(1));
	}
}
