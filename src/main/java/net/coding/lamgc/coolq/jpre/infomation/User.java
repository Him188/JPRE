package net.coding.lamgc.coolq.jpre.infomation;

import net.coding.lamgc.coolq.jpre.binary.Unpack;

/**
 * 用户信息
 *
 * @author Him188
 */
public class User {
	public final long QQ;
	public final String nick;
	public final int age;
	public final int gender;

	public User(long QQ, String nick, int age, int gender) {
		this.QQ = QQ;
		this.nick = nick;
		this.age = age;
		this.gender = gender;
	}

	public User(Unpack binary) {
		this(binary.getLong(), binary.getString(), binary.getInt(), binary.getInt());
	}

	public User(byte[] data) {
		this(new Unpack(data));
	}

	public long getQQ() {
		return QQ;
	}

	public int getAge() {
		return age;
	}

	public int getGender() {
		return gender;
	}

	public String getNick() {
		return nick;
	}
}
