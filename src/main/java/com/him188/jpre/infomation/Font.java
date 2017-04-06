package com.him188.jpre.infomation;

import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class Font {
	/*
	.数据类型 数_字体, 公开
    .成员 名称, 文本型
    .成员 字号, 整数型
    .成员 颜色, 整数型
    .成员 样式, 整数型, , , 粗体：1 斜体：2 下划线：4
    .成员 气泡, 整数型
	*/
	public final String name; //名称
	public final int size; //字号
	public final int color; //颜色, RGB
	public final int style; //粗体: 1, 斜体:2, 下划线: 4. 请使用位与
	public final int bubble; //气泡

	public Font(String name, int size, int color, int style, int bubble) {
		this.name = name;
		this.size = size;
		this.color = color;
		this.style = style;
		this.bubble = bubble;
	}

	public Font(Unpack unpack) {
		this(unpack.getString(), unpack.getInt(), unpack.getInt(), unpack.getInt(), unpack.getInt());
	}

	public Font(byte[] data) {
		this(new Unpack(data));
	}

	public int getBubble() {
		return bubble;
	}

	public int getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}

	public int getStyle() {
		return style;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Font(name=" + name + ",size=" + size + ",color=" + color + ",style=" + style + ",bubble=" + bubble;
	}
}
