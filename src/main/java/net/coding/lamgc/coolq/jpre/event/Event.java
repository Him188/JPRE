package net.coding.lamgc.coolq.jpre.event;

import net.coding.lamgc.coolq.jpre.event.action.replay.ReplayDiscussMessageEvent;
import net.coding.lamgc.coolq.jpre.event.action.replay.ReplayGroupMessageEvent;
import net.coding.lamgc.coolq.jpre.event.action.replay.ReplayPrivateMessageEvent;
import net.coding.lamgc.coolq.jpre.event.action.send.SendDiscussMessageEvent;
import net.coding.lamgc.coolq.jpre.event.action.send.SendGroupMessageEvent;
import net.coding.lamgc.coolq.jpre.event.action.send.SendPrivateMessageEvent;
import net.coding.lamgc.coolq.jpre.event.coolq.CoolQExitEvent;
import net.coding.lamgc.coolq.jpre.event.friend.FriendAddEvent;
import net.coding.lamgc.coolq.jpre.event.group.GroupAdminChangeEvent;
import net.coding.lamgc.coolq.jpre.event.group.GroupFileUploadEvent;
import net.coding.lamgc.coolq.jpre.event.group.GroupMemberDecreaseEvent;
import net.coding.lamgc.coolq.jpre.event.group.GroupMemberIncreaseEvent;
import net.coding.lamgc.coolq.jpre.event.message.DiscussMessageEvent;
import net.coding.lamgc.coolq.jpre.event.message.GroupMessageEvent;
import net.coding.lamgc.coolq.jpre.event.message.PrivateMessageEvent;
import net.coding.lamgc.coolq.jpre.event.plugin.PluginDisableEvent;
import net.coding.lamgc.coolq.jpre.event.plugin.PluginEnableEvent;
import net.coding.lamgc.coolq.jpre.event.request.AddFriendRequestEvent;
import net.coding.lamgc.coolq.jpre.event.request.AddGroupRequestEvent;
import net.coding.lamgc.coolq.jpre.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Him188
 */
abstract public class Event {
	private static final Class[] REGISTERED_EVENTS = new Class[64];
	private static final int[] REGISTERED_TYPES = new int[64];
	private static int COUNT = 0;

	static {
		registerEvent(GroupAdminChangeEvent.class);
		registerEvent(GroupFileUploadEvent.class);
		registerEvent(GroupMemberDecreaseEvent.class);
		registerEvent(GroupMemberIncreaseEvent.class);

		registerEvent(DiscussMessageEvent.class);
		registerEvent(GroupMessageEvent.class);
		registerEvent(PrivateMessageEvent.class);

		registerEvent(PluginDisableEvent.class);
		registerEvent(PluginEnableEvent.class);

		registerEvent(ReplayDiscussMessageEvent.class);
		registerEvent(ReplayGroupMessageEvent.class);
		registerEvent(ReplayPrivateMessageEvent.class);

		registerEvent(SendDiscussMessageEvent.class);
		registerEvent(SendGroupMessageEvent.class);
		registerEvent(SendPrivateMessageEvent.class);

		registerEvent(FriendAddEvent.class);

		registerEvent(AddFriendRequestEvent.class);
		registerEvent(AddGroupRequestEvent.class);

		registerEvent(CoolQExitEvent.class);
	}

	private boolean cancelled = false;

	private boolean intercepted = false;

	public static Class[] getRegisteredEvents() {
		return REGISTERED_EVENTS;
	}

	public static int[] getRegisteredTypes() {
		return REGISTERED_TYPES;
	}

	@SuppressWarnings("unchecked")
	public static Event matchEvent(int type, Object... args) {
		for (int i = 0; i < REGISTERED_TYPES.length; i++) {
			if (REGISTERED_TYPES[i] == type) {
				try {
					//can not use args.getClass(). if use, it will be Object[].class
					Class<?>[] classes = new Class<?>[args.length];
					for (int i1 = 0; i1 < args.length; i1++) {
						classes[i1] = args[i1].getClass();
					}
					Constructor constructor;
					if (classes.length == 0) {
						constructor = REGISTERED_EVENTS[i].getConstructor();
					} else {
						constructor = REGISTERED_EVENTS[i].getConstructor(classes);
					}

					constructor.setAccessible(true);
					return (Event) constructor.newInstance(args);
				} catch (Exception e) {
					continue;
				}
			}
		}

		return null;
	}

	/**
	 * 注册事件.
	 * 插件需要新增一个自定义事件时必须调用本方法. (建议在 {@link Plugin#onLoad} 中调用)
	 *
	 * @param eventClass Event class
	 *
	 * @return 当 {@code eventClass} 没有继承 {@link Event} 或 {@code eventClass} 已经被注册 或 {@code eventClass} 中不存在 {@code
	 * getEventType()I} 方法时为 false. 成功为 true
	 */
	public static boolean registerEvent(Class<?> eventClass) {
		if (!eventClass.isAssignableFrom(Event.class)) {
			return false;
		}

		for (Class registeredEvent : REGISTERED_EVENTS) {
			if (registeredEvent.equals(eventClass)) {
				return false;
			}
		}

		try {
			Method method = eventClass.getMethod("getEventType");
			method.setAccessible(true);

			int result = (int) method.invoke(null);

			REGISTERED_TYPES[COUNT] = result;
			REGISTERED_EVENTS[COUNT++] = eventClass;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isCancelled() {
		return cancelled;
	}

	@SuppressWarnings("SameParameterValue")
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * 取消这个事件.
	 * 当事件被取消后, 插件可以自行进行回复,
	 * 一些设置了忽略被取消事件的事件处理器 ({@link EventHandler#ignoreCancelled()} 为 false) 就不会收到事件,
	 * 并且, 事件系统不会进行自动回复.
	 * 例如:
	 * <p>
	 * 事件: {@link AddFriendRequestEvent}, 且设置为接受请求.
	 * 如果不取消事件, 事件系统最终会同意该请求.
	 * 如果取消事件, 事件系统不会进行处理. 注意! 是不会处理, 而不是拒绝!
	 */
	public void setCancelled() {
		setCancelled(true);
	}

	/**
	 * 拦截这个事件. 不会影响事件处理, 仅仅只是起到标识作用.
	 * 插件可根据需要进行忽略处理.
	 */
	public void setIntercepted() {
		setIntercepted(true);
	}

	public boolean isIntercepted() {
		return intercepted;
	}

	@SuppressWarnings("SameParameterValue")
	public void setIntercepted(boolean intercepted) {
		this.intercepted = intercepted;
	}

	/**
	 * 当所有事件处理器都处理过事件后(即事件处理已经达到尾声时), 本方法被调用
	 * JPRE自带的所有事件中, 都没有复写本方法. 本方法只是留给插件自定义事件用.
	 */
	public void close() {

	}
}
