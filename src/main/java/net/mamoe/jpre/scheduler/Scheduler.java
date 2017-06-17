package net.mamoe.jpre.scheduler;

import net.mamoe.jpre.JPREMain;
import net.mamoe.jpre.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Him188 @ JPRE Project
 */
public class Scheduler extends Thread {
	public static final int WORKER_COUNT = Math.max(4, Runtime.getRuntime().availableProcessors() * 2);
	private final JPREMain main;

	private Map<Integer, Worker> workers = new HashMap<>(WORKER_COUNT);

	private long heartbeatDelay;

	public Scheduler(int heartbeatDelay, JPREMain main) {
		this.main = main;
		for (int i = 0; i < WORKER_COUNT; i++) {
			Worker worker = new Worker();
			worker.start();
			workers.put(worker.getWorkerId(), worker);
		}

		this.heartbeatDelay = heartbeatDelay;
	}

	public long getHeartbeatDelay() {
		return heartbeatDelay;
	}

	public Map<Integer, Worker> getWorkers() {
		return workers;
	}

	public TaskHandler[] addMethodTasks(Plugin plugin, Object object) {
		Objects.requireNonNull(object);
		Class<?> clazz = object.getClass();

		Collection<Method> methods = new HashSet<>();
		Collections.addAll(methods, clazz.getMethods());
		Collections.addAll(methods, clazz.getDeclaredMethods());
		Collection<TaskHandler> tasks = new ArrayList<>();
		for (Method method : methods) {
			TaskHandler task = this.addTask(plugin, object, method);
			if (task != null) {
				tasks.add(task);
			}
		}

		return tasks.toArray(new TaskHandler[tasks.size()]);
	}

	public TaskHandler addTask(Plugin plugin, Object object, Method method) {
		Objects.requireNonNull(plugin);
		Objects.requireNonNull(method);

		if (method.getParameterCount() != 0) {
			return null;
		}

		TaskInfo annotation;
		try {
			annotation = method.getAnnotation(TaskInfo.class);
		} catch (NullPointerException ignored) {
			return null;
		}

		if (annotation != null) {
			return this.addTask(new PluginTask(plugin, annotation) {
				@Override
				public void onRun() {
					method.setAccessible(true);
					try {
						method.invoke(object);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		return null;
	}


	public TaskHandler addTask(Plugin plugin, Runnable task, TaskInfo info) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(new PluginTask(plugin, info) {
			@Override
			public void onRun() {
				task.run();
			}
		});
	}

	public TaskHandler addTask(Plugin plugin, Runnable task) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(plugin, task, 0, -1);
	}

	public TaskHandler addTask(Plugin plugin, Runnable task, long delay) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(plugin, task, delay, -1);
	}

	public TaskHandler addTask(Plugin plugin, Runnable task, long delay, long period) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(new PluginTask(plugin, delay, period) {
			@Override
			public void onRun() {
				task.run();
			}
		});
	}


	public TaskHandler addTask(int workerId, Plugin plugin, Runnable task, TaskInfo info) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(workerId, plugin, task, info.delay(), info.period());
	}

	public TaskHandler addTask(int workerId, Plugin plugin, Runnable task) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(workerId, plugin, task, 0, -1);
	}

	public TaskHandler addTask(int workerId, Plugin plugin, Runnable task, long delay) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(workerId, plugin, task, delay, -1);
	}

	public TaskHandler addTask(int workerId, Plugin plugin, Runnable task, long delay, long period) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(workerId, new PluginTask(plugin, delay, period) {
			@Override
			public void onRun() {
				task.run();
			}
		});
	}


	public TaskHandler addTask(Worker worker, Plugin plugin, Runnable task, TaskInfo info) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(worker, plugin, task, info.delay(), info.period());
	}

	public TaskHandler addTask(Worker worker, Plugin plugin, Runnable task) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(worker, plugin, task, 0, -1);
	}

	public TaskHandler addTask(Worker worker, Plugin plugin, Runnable task, long delay) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(worker, plugin, task, delay, -1);
	}

	public TaskHandler addTask(Worker worker, Plugin plugin, Runnable task, long delay, long period) {
		if (task instanceof PluginTask) {
			return this.addTask((Task) task);
		}
		return this.addTask(worker, new PluginTask(plugin, delay, period) {
			@Override
			public void onRun() {
				task.run();
			}
		});
	}


	public TaskHandler addTask(Runnable task) {
		return this.addTask(null, task);
	}

	public TaskHandler addTask(Task task) {
		Objects.requireNonNull(task, "argument task must not be null");

		Worker minWorker = this.workers.get(0);
		int minSize = minWorker.getQueue().size();
		for (Worker worker : this.workers.values()) {
			if (worker.getQueue().size() < minSize) {
				minWorker = worker;
			}
		}

		return this.addTask(minWorker, task);
	}

	public TaskHandler addTask(int workerId, Task task) throws IllegalArgumentException {
		Objects.requireNonNull(task, "argument task must not be null");
		Worker worker = this.workers.get(workerId);
		if (worker == null) {
			throw new IllegalArgumentException("worker#" + workerId + " does not exists");
		}

		return this.addTask(worker, task);
	}

	public TaskHandler addTask(Worker worker, Task task) {
		Objects.requireNonNull(worker, "argument worker must not be null");
		Objects.requireNonNull(task, "argument task must not be null");

		return worker.joinQueue(new TaskHandler(task));
	}

	@Override
	public void run() {
		try {
			while (main.isRunning()) {
				for (Worker worker : this.workers.values()) {
					worker.setWaiting(false);
				}

				Thread.sleep(heartbeatDelay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
