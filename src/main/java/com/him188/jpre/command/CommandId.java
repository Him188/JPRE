package com.him188.jpre.command;

/**
 * @author Him188
 */
public enum CommandId {
	SEND_PRIVATE_MESSAGE(1, int.class, long.class, String.class),
	SEND_GROUP_MESSAGE(2, int.class, long.class, String.class),
	SEND_DISCUSS_MESSAGE(3, int.class, long.class, String.class),
	SEND_LIKE(4, int.class, long.class),
	SEND_LIKE_V2(5, int.class, long.class, int.class),
	GET_COOKIES(6, int.class),
	GET_RECORD(7, int.class, String.class, String.class),
	GET_CSRF_TOKEN(8, int.class),
	GET_APP_DIRECTORY(9, int.class),
	GET_LOGIN_QQ(10, int.class),
	GET_LOGIN_NICK(11, int.class),
	SET_GROUP_KICK(12, int.class, long.class, long.class, boolean.class),
	SET_GROUP_BAN(13, int.class, long.class, long.class, long.class),
	SET_GROUP_SPECIAL_TITLE(14, int.class, long.class, long.class, String.class),
	SET_GROUP_WHOLE_BAN(15, int.class, long.class, boolean.class),
	SET_GROUP_ANONYMOUS_BAN(16, int.class, long.class, String.class, long.class),
	SET_GROUP_CARD(17, int.class, long.class, long.class, String.class),
	SET_GROUP_LEAVE(18, int.class, long.class, boolean.class),
	SET_DISCUSS_LEAVE(19, int.class, long.class),
	SET_FRIEND_ADD_REQUEST(20, int.class, String.class, int.class, String.class),
	SET_GROUP_ADD_REQUEST(21, int.class, String.class, int.class, int.class),
	SET_GROUP_ADD_REQUEST_V2(22, int.class, String.class, int.class, int.class, String.class),
	ADD_LOG(23, int.class, String.class, String.class),
	SET_FATAL(24, int.class, String.class),
	GET_GROUP_MEMBER_INFO(25, int.class, long.class, long.class),
	GET_GROUP_MEMBER_INFO_V2(26, int.class, long.class, long.class, boolean.class),
	GET_STRANGER_INFO(27, int.class, long.class, boolean.class),

	SET_GROUP_ADMIN(28, int.class, long.class, long.class, boolean.class),
	SET_GROUP_ANONYMOUS(29, int.class, long.class, boolean.class),;

	private final int id;
	private final Class[] args;

	CommandId(int id, Class... args) {
		this.id = id;
		this.args = args;
	}

	CommandId(int id) {
		this(id, new Class[]{});
	}

	public Class[] getArgs() {
		return args;
	}

	public int getId() {
		return id;
	}
}
