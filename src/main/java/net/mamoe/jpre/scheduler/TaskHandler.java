package net.mamoe.jpre.scheduler;

/**
 * 用来管理一个任务 ({@link Task}). <br>
 * 可通过 {@link Scheduler#addTask} 来创建任务并得到这个对象 <br>
 * <br>
 * 该对象能管理任务的延迟和循环. 你也可以在这里取消任务 ({@link TaskHandler#cancelTask()})
 *
 * @author Him188 @ JPRE Project
 */
public class TaskHandler {
	private Worker worker;
	private final Task task;

	TaskHandler(Task task) {
		this.task = task;
		this.delay = task.getDelay();
		this.period = task.getPeriod();

		this.targetTime = System.currentTimeMillis() + this.delay;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public Worker getWorker() {
		return worker;
	}

	public Task getTask() {
		return task;
	}

	/**
	 * 取消任务.<br>
	 * 对于延迟任务和循环任务, 将立即从 {@link Worker} 的任务队列中移除.<br>
	 * 对于立即运行的任务, 本方法没有任何作用.
	 */
	public void cancelTask() {
		this.task.setCancelled(true);
	}

	public boolean isTaskCancelled() {
		return this.task.isCancelled();
	}

	private final long delay;
	private final long period;

	private long targetTime;

	public long getDelay() {
		return delay;
	}

	public long getPeriod() {
		return period;
	}

	public boolean isRepeating() {
		return getPeriod() != -1;
	}

	public long getTargetTime() {
		return targetTime;
	}

	public void processTime() {
		if (this.getTask().isCancelled()) {
			return;
		}

		if (System.currentTimeMillis() >= this.getTargetTime()) {
			this.getTask().run();
			if (this.isRepeating()) {
				this.targetTime = System.currentTimeMillis() + this.getPeriod();
			} else {
				this.getTask().setCancelled(true);
			}
		}
	}
}
