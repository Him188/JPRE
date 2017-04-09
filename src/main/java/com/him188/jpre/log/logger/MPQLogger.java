package com.him188.jpre.log.logger;

import com.him188.jpre.JPREMain;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.log.Log;
import com.him188.jpre.log.Priority;


import static com.him188.jpre.log.Priority.*;

/**
 * 写入MPQ日志
 * <p>
 * 写入插件日志的记录器: {@link PluginLogger}
 *
 * @see JPREMain#getLogger()
 */
public class MPQLogger implements Logger {
	@Override
	public void log(Priority priority, String type, String message) {
		RobotQQ.output(new Log(priority, type, message).toString());
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
