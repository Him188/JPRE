package com.him188.jpre.log;

import java.util.HashMap;
import java.util.Map;

/**
 * 记录器优先级
 */
public enum Priority {
	INFO(0),
	NOTICE(1),
	WARNING(2),
	DEBUG(3),
	ERROR(4),
	FATAL(5);

	public static final Map<Integer, Priority> LIST = new HashMap<Integer, Priority>() {
		{
			put(INFO.id, INFO);
			put(NOTICE.id, NOTICE);
			put(WARNING.id, WARNING);
			put(DEBUG.id, DEBUG);
			put(ERROR.id, ERROR);
			put(FATAL.id, FATAL);
		}
	};

	public final int id;

	Priority(int id) {
		this.id = id;
	}

	public static Priority fromInteger(int id) {
		return LIST.get(id);
	}

	public int getId() {
		return id;
	}
}