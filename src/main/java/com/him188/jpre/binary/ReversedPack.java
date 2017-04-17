package com.him188.jpre.binary;

import static com.him188.jpre.binary.Binary.*;

/**
 * @author Him188
 */
public class ReversedPack extends Pack{
	public ReversedPack(Pack pack) {
		this(pack.getData());
	}

	public ReversedPack() {
		this(new byte[]{});
	}

	public ReversedPack(byte[] data) {
		setData(data);
	}

	@Override
	public byte[] getBytes(int length) {
		byte[] result = getCenter(location, length);


		location += length;
		return result;
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
		return new String(realReverse(getBytes(getInt())));
	}
}
