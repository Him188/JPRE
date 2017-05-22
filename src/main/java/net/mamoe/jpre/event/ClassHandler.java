package net.mamoe.jpre.event;

import net.mamoe.jpre.plugin.Plugin;

/**
 * 类事件处理器.
 * JPRE没有提供自动创建方法
 *
 * @author Him188 @ JPRE Project
 */
public abstract class ClassHandler implements Handler {
	private final Listener listener;
	private final Class<Event> event;
	private final Plugin plugin;
	private final EventPriority priority;

	public ClassHandler(Plugin plugin, Listener listener, Class<Event> event, EventPriority priority) {
		this.plugin = plugin;
		this.listener = listener;
		this.event = event;
		this.priority = priority;
	}

	public abstract void run(Event event);

	@Override
	public Class<Event> getEvent() {
		return event;
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	@Override
	public Listener getListener() {
		return listener;
	}

	@Override
	public void execute(Listener listener, Event event) {
		if (!getListener().equals(listener)) {
			return;
		}

		EventHandler handler = this.getClass().getAnnotation(EventHandler.class);
		if (handler == null) {
			return;
		}

		if (handler.ignoreCancelled() && event.isCancelled()) {
			return;
		}

		if (handler.ignoreIntercepted() && event.isIntercepted()) {
			return;
		}

		run(event);
	}

	@Override
	public EventPriority getPriority() {
		return priority;
	}

	@Override
	public String toString() {
		return "ClassHandler(plugin=" + getPlugin().getName() + ",name=" + getClass().getSimpleName() + ")";
	}
}
