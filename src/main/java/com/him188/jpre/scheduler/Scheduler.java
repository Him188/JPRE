package com.him188.jpre.scheduler;

import com.him188.jpre.JPREMain;
import com.him188.jpre.plugin.Plugin;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 延迟任务管理器.
 * <p>
 * 本类内置了 {@link ScheduledThreadPoolExecutor} 和 {@link ThreadPoolExecutor},
 * 你可以在这里轻松地创建并管理延迟(循环)任务
 *
 * @author Him188
 */
public class Scheduler {
	protected static final ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(10);

	protected static final ThreadPoolExecutor pool = new ThreadPoolExecutor(32, 128, 1000 * 60, TimeUnit.MILLISECONDS, new SynchronousQueue<>());

	/**
	 * 新建延迟任务. 该任务只会被执行一次
	 *
	 * @param plugin 插件
	 * @param task   任务
	 * @param delay  延迟时间. 单位毫秒
	 *
	 * @return 是否成功
	 */
	public static Task scheduleTimingTask(Plugin plugin, Runnable task, long delay) {
		return scheduleTimingTask(plugin, new Task() {
			@Override
			public void onRun() {
				task.run();
			}
		}, delay);
	}

	public static Task scheduleTimingTask(Plugin plugin, Task task, long delay) {
		if (plugin != null && !plugin.isEnabled()) {
			return null;
		}
		task.setOwner(plugin);
		service.schedule(task, delay, TimeUnit.MILLISECONDS);
		return task;
	}

	/**
	 * 新建延迟任务. 该任务会被重复执行直到
	 *
	 * @param plugin 插件
	 * @param task   任务
	 * @param delay  延迟时间. 单位毫秒
	 *
	 * @return 是否成功
	 */
	public static Task scheduleRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
		return scheduleRepeatingTask(plugin, new Task() {
			@Override
			public void onRun() {
				task.run();
			}
		}, delay, period);
	}

	public static Task scheduleRepeatingTask(Plugin plugin, Task task, long delay, long period) {
		if (plugin != null && !plugin.isEnabled()) {
			return null;
		}
		task.setOwner(plugin);
		service.scheduleWithFixedDelay(task, delay, period, TimeUnit.MILLISECONDS);
		return task;
	}

	/**
	 * 新建异步任务. 该任务只会被执行一次
	 *
	 * @param plugin 插件
	 * @param task   任务
	 *
	 * @return 是否成功
	 */
	public static Task scheduleTask(Plugin plugin, Runnable task) {
		return scheduleTask(plugin, new Task() {
			@Override
			public void onRun() {
				task.run();
			}
		});
	}

	public static Task scheduleTask(Plugin plugin, Task task) {
		if (plugin != null && !plugin.isEnabled()) {
			return null;
		}
		task.setOwner(plugin);
		pool.execute(task);
		return task;
	}


	/**
	 * @see Task#cancel()
	 */
	public static void cancelTask(Task task) {
		task.cancel();
	}

	public static void shutdown(){
		if (JPREMain.isShutdown()) {
			service.shutdownNow();
			pool.shutdownNow();
		}
	}
}
