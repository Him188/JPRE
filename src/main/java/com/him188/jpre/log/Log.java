package com.him188.jpre.log;

/**
 * 一条日志
 */
public class Log {
	public Priority priority;
	public String type;
	public String message;

	public Log(Priority priority, String type, String message) {
		this.priority = priority;
		this.type = type;
		this.message = message;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null
				&& obj instanceof Log
				&& ((Log) obj).priority == priority
				&& ((((Log) obj).message == null && message == null) || ((Log) obj).message.equals(message))
				&& ((Log) obj).type.equals(type);
	}

	@Override
	public String toString() {
		return priority.id + "||" + type + "||" + message;
	}
}