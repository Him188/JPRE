package net.coding.lamgc.coolq.jpre.log;

import net.coding.lamgc.coolq.jpre.log.logger.Logger;

import java.util.List;
import java.util.Vector;

/**
 * 日志管理器. 所有日志记录器记录的日志最终均会添加到此处.
 */
public class LogManager {
	private static final List<Log> logs = new Vector<>();

	private LogManager() {
		throw new RuntimeException("You can not create a instance of LogManager");
	}

	/**
	 * 不建议直接使用本方法, 请参考 {@link Logger}
	 *
	 * @param log 日志
	 */
	public static void addLog(Log log) {
		logs.add(log);
	}

	/**
	 * 获取并删除日志列表第一个日志. 当日志列表中不存在任何日志时, 本方法会阻塞线程直到可以取出日志
	 *
	 * @return 第一个日志
	 */
	@SuppressWarnings("StatementWithEmptyBody")
	public static Log getFirst() {
		while (logs.size() == 0) ;
		return logs.remove(0);
	}
}
