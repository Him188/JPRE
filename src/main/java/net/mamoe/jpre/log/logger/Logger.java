package net.mamoe.jpre.log.logger;

import net.mamoe.jpre.log.Priority;

/**
 * @author Him188
 */
public interface Logger {
	void log(Priority priority, String type, String message);


	void info(String type, String message);

	void error(String type, String message);

	void debug(String type, String message);

	void notice(String type, String message);

	void warning(String type, String message);

	void fatal(String type, String message);
}
