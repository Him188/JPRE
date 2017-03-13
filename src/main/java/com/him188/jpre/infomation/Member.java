package com.him188.jpre.infomation;

import com.him188.jpre.binary.Unpack;

/**
 * 群成员信息
 *
 * @author Him188
 */
public class Member {
	public final long group;
	public final long QQ;
	public final String nick;
	public final String card; //名片
	public final int gender;
	public final int age;
	public final String location;//地区
	public final int joinTime; //加群时间
	public final int lastSpeaking; //最后发言
	public final String level;//群等级
	public final boolean bad;//不良成员. 不良: 1
	public final String specialTitle; //专属头衔
	public final int titleTime;//专属头衔过期时间
	public final boolean allowModify;//允许修改群名片, 允许: 1

	public Member(long group, long QQ, String nick, String card, int gender, int age, String location, int joinTime, int lastSpeaking, String level, int bad, String specialTitle, int titleTime, int allowModify) {
		this.group = group;
		this.QQ = QQ;
		this.nick = nick;
		this.card = card;
		this.gender = gender;
		this.age = age;
		this.location = location;
		this.joinTime = joinTime;
		this.lastSpeaking = lastSpeaking;
		this.level = level;
		this.bad = bad == 1;
		this.specialTitle = specialTitle;
		this.titleTime = titleTime;
		this.allowModify = allowModify == 1;
	}

	public Member(Unpack unpack) {
		this(unpack.getLong(),
				unpack.getLong(),
				unpack.getString(),
				unpack.getString(),
				unpack.getInt(),
				unpack.getInt(),
				unpack.getString(),
				unpack.getInt(),
				unpack.getInt(),
				unpack.getString(),
				unpack.getInt(),
				unpack.getString(),
				unpack.getInt(),
				unpack.getInt());
	}

	public Member(byte[] data) {
		this(new Unpack(data));
	}

	public String getNick() {
		return nick;
	}

	public int getAge() {
		return age;
	}

	public long getQQ() {
		return QQ;
	}

	public long getGroup() {
		return group;
	}

	public String getCard() {
		return card;
	}

	public String getLevel() {
		return level;
	}

	public String getLocation() {
		return location;
	}

	public String getSpecialTitle() {
		return specialTitle;
	}

	public int getJoinTime() {
		return joinTime;
	}

	public int getLastSpeaking() {
		return lastSpeaking;
	}

	public boolean isAllowModify() {
		return allowModify;
	}

	public boolean isBad() {
		return bad;
	}

	public int getGender() {
		return gender;
	}

	public int getTitleTime() {
		return titleTime;
	}
}
