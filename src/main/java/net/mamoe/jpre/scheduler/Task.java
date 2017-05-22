package net.mamoe.jpre.scheduler;

import net.mamoe.jpre.plugin.Plugin;

/**
 * 延迟任务
 *
 * @author Him188 @ JPRE Project
 */
@SuppressWarnings("WeakerAccess")
public abstract class Task implements Runnable {
	/* Abstract */

	/**
	 * 计时时间到达后调用本方法
	 */
	public abstract void onRun();


	/* Scheduler */
	private Scheduler scheduler;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}


	private boolean cancelled = false;

	public boolean isCancelled() {
		return cancelled;
	}

	private void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}


	protected Plugin owner;

	protected final void setOwner(Plugin owner) {
		this.owner = owner;
	}


	protected final Plugin getOwner() {
		return owner;
	}

	/**
	 * 取消这个任务, 在任务计时时间到达前可通过 {@link #start()} 重新激活任务
	 */
	public void cancel() {
		setCancelled(true);
	}

	/**
	 * 重新激活任务
	 */
	public void start() {
		setCancelled(false);
	}

	/**
	 * 取消任务并从任务池中删除 (不可重新激活任务)
	 */
	public void forceCancel() {
		cancel();
		getScheduler().service.remove(this);
		getScheduler().pool.remove(this);
	}

	@Override
	public void run() {
		if (!isCancelled()) {
			onRun();
		}
	}
}
