package net.coding.lamgc.coolq.jpre.event;

import net.coding.lamgc.coolq.jpre.PluginManager;
import net.coding.lamgc.coolq.jpre.plugin.Plugin;

import java.lang.reflect.Method;

/**
 * 方法事件处理器
 * 由 {@link PluginManager#registerEvent(Listener, Plugin, Class, Method)} 自动创建
 * 一般不会在插件中使用
 *
 * @author Him188
 */
public class MethodHandler implements Handler {
	private final Listener listener;
	private final Class<Event> event;
	private final Plugin plugin;
	private final Method method;

	private final EventPriority priority;

	public MethodHandler(Plugin plugin, Listener listener, Method method, Class<Event> event) {
		this.plugin = plugin;
		this.listener = listener;
		this.event = event;
		this.method = method;

		EventHandler handler = method.getAnnotation(EventHandler.class);
		if (handler == null) {
			priority = EventPriority.NORMAL;
		} else {
			priority = handler.priority();
		}
	}

	@Override
	public Class<Event> getEvent() {
		return event;
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	public Listener getListener() {
		return listener;
	}

	@Override
	public void execute(Listener listener, Event event) {
		if (!getListener().equals(listener)) {
			return;
		}

		EventHandler handler = method.getAnnotation(EventHandler.class);
		if (handler == null) {
			return;
		}

		if (handler.ignoreCancelled() && event.isCancelled()) {
			return;
		}

		if (handler.ignoreIntercepted() && event.isIntercepted()) {
			return;
		}

		for (Class<?> parameter : method.getParameterTypes()) {

			if (parameter.isAssignableFrom(this.event)) {
				try {
					method.setAccessible(true);
					method.invoke(listener, event);
					return;
				} catch (Exception e) {
					return;
				}
			}
		}
	}

	@Override
	public EventPriority getPriority() {
		return priority;
	}
}
