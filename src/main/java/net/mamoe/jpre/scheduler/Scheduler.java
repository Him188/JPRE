package net.mamoe.jpre.scheduler;

import javafx.concurrent.Worker;
import net.mamoe.jpre.Frame;
import net.mamoe.jpre.plugin.Plugin;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 延迟任务管理器.
 * <p>
 * 本类内置了 {@link ScheduledThreadPoolExecutor} 和 {@link ThreadPoolExecutor},
 * 你可以在这里轻松地创建并管理延迟(循环)任务
 * @author Him188 @ JPRE Project */
public class Scheduler { // TODO: 2017/5/14  NEW SCHEDULER (worker)
    private Frame frame;


    private ConcurrentLinkedQueue<Task> tasks;

    private ArrayList<>

	public Scheduler(Frame frame) {
		this.frame = frame;
	}

	public Frame getFrame() {
		return frame;
	}

	/**
	 * 新建延迟任务. 该任务只会被执行一次
	 *
	 * @param plugin 插件
	 * @param task   任务
	 * @param delay  延迟时间. 单位毫秒
	 *
	 * @return 是否成功
	 */
	public Task scheduleTimingTask(Plugin plugin, Runnable task, long delay) {
		return scheduleTimingTask(plugin, new Task() {
			@Override
			public void onRun() {
				task.run();
			}
		}, delay);
	}

	public Task scheduleTimingTask(Plugin plugin, Task task, long delay) {
		if (plugin != null && !plugin.isEnabled()) {
			return null;
		}

		task.setScheduler(this);
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
	public Task scheduleRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
		return scheduleRepeatingTask(plugin, new Task() {
			@Override
			public void onRun() {
				task.run();
			}
		}, delay, period);
	}

	public Task scheduleRepeatingTask(Plugin plugin, Task task, long delay, long period) {
		if (plugin != null && !plugin.isEnabled()) {
			return null;
		}
		task.setScheduler(this);
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
	public Task scheduleTask(Plugin plugin, Runnable task) {
		return scheduleTask(plugin, new Task() {
			@Override
			public void onRun() {
				task.run();
			}
		});
	}

	public Task scheduleTask(Plugin plugin, Task task) {
		if (plugin != null && !plugin.isEnabled()) {
			return null;
		}
		task.setScheduler(this);
		task.setOwner(plugin);
		pool.execute(task);
		return task;
	}


	/**
	 * @see Task#cancel()
	 */
	public void cancelTask(Task task) {
		task.cancel();
	}

	public void shutdown() {
		service.shutdownNow();
		pool.shutdownNow();
	}
}
