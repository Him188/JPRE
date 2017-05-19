package net.mamoe.jpre.scheduler;

import net.mamoe.jpre.Frame;

/**
 * 插件请使用 {@link Frame#getScheduler()} ({@link Scheduler})
 *
 * @author Him188 @ JPRE Project */
public final class ServerScheduler extends Scheduler {
	public ServerScheduler() {
		super(null);
	}

	public Task scheduleRepeatingTask(Runnable task, long delay, long period) {
		return super.scheduleRepeatingTask(null, task, delay, period);
	}

	public Task scheduleRepeatingTask(Task task, long delay, long period) {
		return super.scheduleRepeatingTask(null, task, delay, period);
	}

	public Task scheduleTask(Runnable task) {
		return super.scheduleTask(null, task);
	}

	public Task scheduleTask(Task task) {
		return super.scheduleTask(null, task);
	}

	public Task scheduleTimingTask(Runnable task, long delay) {
		return super.scheduleTimingTask(null, task, delay);
	}

	public Task scheduleTimingTask(Task task, long delay) {
		return super.scheduleTimingTask(null, task, delay);
	}
}
