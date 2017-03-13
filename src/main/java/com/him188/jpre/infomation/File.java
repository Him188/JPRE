package com.him188.jpre.infomation;

import com.him188.jpre.binary.Unpack;

/**
 * 群文件
 *
 * @author Him188
 */
@SuppressWarnings("SpellCheckingInspection")
public class File {
	public final String id;
	public final String name;
	public final long size;//unit: byte
	public final long busid;

	public File(String id, String name, long size, long busid) {
		this.id = id;
		this.name = name;
		this.size = size;
		this.busid = busid;
	}

	public File(Unpack unpack) {
		this(unpack.getString(), unpack.getString(), unpack.getLong(), unpack.getLong());
	}

	public File(byte[] data) {
		this(new Unpack(data));
	}

	public long getBusid() {
		return busid;
	}

	public long getSize() {
		return size;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
