package com.him188.jpre.scheduler;

import com.him188.jpre.Frame;
import com.him188.jpre.plugin.Plugin;

/**
 * 延迟任务
 *
 * @author Him188
 */
@SuppressWarnings("WeakerAccess")
public abstract class Task implements Runnable {
	/**
	 * 计时时间到达后调用本方法
	 */
	public abstract void onRun();


	private boolean cancelled = false;

	public boolean isCancelled() {
		return cancelled;
	}

	private void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}


	protected Plugin owner;

	protected final void setOwner(Plugin owner){
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
		.remove(this);
		Scheduler.pool.remove(this);
	}

	@Override
	public void run() {
		if (!isCancelled()) {
			onRun();
		}
	}
}
