package com.him188.jpre.binary;

import static com.him188.jpre.binary.Binary.*;

/**
 * @author Him188
 */
public class ReversedUnpack extends Unpack{

	public ReversedUnpack(Pack pack) {
		this(pack.getData());
	}

	public ReversedUnpack() {
		this(new byte[]{});
	}

	public ReversedUnpack(byte[] data) {
		setData(data);
	}

	@Override
	public byte[] getBytes(int length) {
		byte[] result = getCenter(location, length);


		location += length;
		return result;
	}

	@Override
	public int getLength() {
		return data.length - location;
	}

	@Override
	public byte getByte() {
		return getBytes(1)[0];
	}

	@Override
	public int getInt() {
		return toInt(realReverse(getBytes(4)));
	}

	@Override
	public long getLong() {
		return toLong(realReverse(getBytes(8)));
	}

	@Override
	public short getShort() {
		return toShort(realReverse(getBytes(2)));
	}

	@Override
	public String getString() {
		return new String(getBytes(getInt()));
	}

	@Override
	public boolean getBoolean() {
		return (int) getByte() == 1;
	}

	@Override
	public byte[] getToken() {
		return getBytes(getShort());
	}
}
