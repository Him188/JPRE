package net.mamoe.jpre.scheduler;

/**
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
