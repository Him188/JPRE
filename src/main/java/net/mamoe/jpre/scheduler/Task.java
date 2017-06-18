package net.mamoe.jpre.scheduler;

/**
 * 一项 "任务". <br>
 * 对于插件, 请使用 {@link PluginTask}
 *
 * @author Him188 @ JPRE Project
 */
public abstract class Task implements Runnable {
	public abstract void onRun();

	/**
	 * 已创建的 Task 数量
	 */
	public static int taskCount = 0;

	private int id;

	private long period;
	private long delay;

	/**
	 * {@link Task} 构造器, 将会寻找该对象的注解 {@link TaskInfo} 并调用 {@link Task#init(TaskInfo)} <br>
	 * 当找不到注解时会调用 {@link Task#init()}
	 */
	public Task() {
		TaskInfo annotation = null;
		try {
			annotation = this.getClass().getAnnotation(TaskInfo.class);
		} catch (NullPointerException ignored) {
			init();
		}

		if (annotation == null) {
			init();
		} else {
			init(annotation);
		}
	}

	/**
	 * {@link Task} 构造器, 将会使用注解 {@link TaskInfo} 的属性并调用 {@link Task#init(long, long)}
	 */
	public Task(TaskInfo info) {
		init(info.delay(), info.period());
	}

	public Task(long delay) {
		init(delay, -1);
	}

	public Task(long delay, long period) {
		init();
		this.period = period;
		this.delay = delay;
		this.id = taskCount++;
	}

	protected void init(TaskInfo info) {
		init(info.delay(), info.period());
	}

	protected void init() {
		init(0);
	}

	protected void init(long delay) {
		init(delay, -1);
	}

	protected void init(long delay, long period) {
		this.period = period;
		this.delay = delay;
		this.id = taskCount++;
	}

	public long getPeriod() {
		return period;
	}

	public long getDelay() {
		return delay;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Task && this.id == ((Task) obj).getId();
	}

	@Override
	public void run() {
		if (!this.isCancelled()) {
			this.onRun();
		}
	}

	private boolean cancelled = false;

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
