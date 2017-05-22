package net.mamoe.jpre.event;

import net.mamoe.jpre.plugin.Plugin;
import net.mamoe.jpre.plugin.PluginManager;

import java.lang.reflect.Method;

/**
 * 方法事件处理器
 * 由 {@link PluginManager#registerEvent(Listener, Plugin, Class, Handler)} 自动创建
 * 一般不会在插件中使用
 *
 * @author Him188 @ JPRE Project
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

	@Override
	public Listener getListener() {
		return listener;
	}

	public Method getMethod() {
		return method;
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

		if (event.getClass().isAssignableFrom(method.getParameterTypes()[0])) { //is parameter extends event
			try {
				method.setAccessible(true);
				method.invoke(listener, event);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

	@Override
	public EventPriority getPriority() {
		return priority;
	}

	@Override
	public String toString() {
		return "MethodHandler(plugin=" + getPlugin().getName() + ",method=" + getMethod().getName() + ")";
	}
}
