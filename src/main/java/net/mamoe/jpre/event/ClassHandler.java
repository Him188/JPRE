package net.mamoe.jpre.event;

import net.mamoe.jpre.plugin.Plugin;

/**
 * 类事件处理器.
 * JPRE没有提供自动创建方法
 *
 * @author Him188 @ JPRE Project
 */
public abstract class ClassHandler implements Handler {
	private MethodHandler handler;

	public ClassHandler(Plugin plugin, Listener listener, Class<Event> event) {
		try {
			this.handler = new MethodHandler(plugin, listener, this.getClass().getMethod("run"), event);
		} catch (NoSuchMethodException ignored) {
		}
	}

	public abstract void run(Event event);

	@Override
	public Class<Event> getEvent() {
		return handler.getEvent();
	}

	@Override
	public Plugin getPlugin() {
		return handler.getPlugin();
	}

	@Override
	public Listener getListener() {
		return handler.getListener();
	}

	@Override
	public void execute(Listener listener, Event event) {
		this.handler.execute(listener, event);
	}

	@Override
	public EventPriority getPriority() {
		return handler.getPriority();
	}

	@Override
	public String toString() {
		return "ClassHandler(plugin=" + getPlugin().getName() + ",handler=" + handler + ")";
	}
}
