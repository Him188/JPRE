package net.mamoe.jpre.scheduler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * A task worker
 *
 * @author Him188 @ JPRE Project
 */
public class Worker extends Thread {
	private final Queue<TaskHandler> queue = new ConcurrentLinkedDeque<>();

	private final int id;

	private TaskHandler running;

	public static int workerCount = 0;

	private boolean waiting;

	public void setWaiting(boolean waiting) {

		this.waiting = waiting;
	}

	Worker() {
		this.id = workerCount++;
	}

	public TaskHandler getRunning() {
		return running;
	}

	public int getWorkerId() {
		return id;
	}

	public TaskHandler joinQueue(TaskHandler task) {
		task.setWorker(this);
		queue.add(task);
		return task;
	}

	public Queue<TaskHandler> getQueue() {
		return queue;
	}

	public TaskHandler quitQueue(TaskHandler task) {
		this.queue.remove(task);
		return task;
	}

	public TaskHandler quitQueue(int id) {
		LinkedList<TaskHandler> list = new LinkedList<>(queue);
		for (TaskHandler task : list) {
			if (task.getTask().getId() == id) {
				queue.remove(task);
				return task;
			}
		}
		return null;
	}

	@Override
	public void run() {
		while (true) {
			if (waiting) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException ignored) {
					return;
				}
			}

			waiting = true;
			while (!queue.isEmpty()) {
				this.running = queue.peek();
				this.running.processTime();
				if (this.running.getTask().isCancelled()) {
					queue.remove();
					this.running = null;
				}
			}
		}
	}
}
