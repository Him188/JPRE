package com.him188.jpre.log.logger;

import com.him188.jpre.JPREMain;

/**
 * 直接写入酷Q日志的记录器.
 * <p>
 * 写入插件日志的记录器: {@link PluginLogger}
 *
 * @see JPREMain#getLogger()
 */
public class MPQLogger implements Logger {
	public static final int DEBUG = 0;
	public static final int INFO = 10;
	public static final int WARNING = 20;
	public static final int ERROR = 30;
	public static final int FATAL = 40;

	@Override
	public void log(int priority, String type, String message) {
		JPREMain.getCaller().log(priority, type, message);
	}

	@Override
	public void info(String type, String message) {
		log(INFO, type, message);
	}

	@Override
	public void error(String type, String message) {
		log(ERROR, type, message);
	}

	@Override
	public void debug(String type, String message) {
		log(DEBUG, type, message);
	}

	@Override
	public void notice(String type, String message) {
		log(INFO, type, message);
	}

	@Override
	public void warning(String type, String message) {
		log(WARNING, type, message);
	}

	@Override
	public void fatal(String type, String message) {
		log(FATAL, type, message);
	}
}
