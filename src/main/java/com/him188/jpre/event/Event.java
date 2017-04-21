package com.him188.jpre.event;

/**
 * @author Him188
 */
abstract public class Event {
	/* Interception */

	private boolean intercepted = false;

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


	/* Cancellation*/

	private boolean cancelled = false;

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
	 * 事件: {@link com.him188.jpre.event.qq.FriendAddRequestEvent}, 且设置为接受请求.
	 * 如果不取消事件, 事件系统最终会同意该请求.
	 * 如果取消事件, 事件系统不会进行处理. 注意! 是不会处理, 而不是拒绝!
	 */
	public void setCancelled() {
		setCancelled(true);
	}


	/* Others */

	/**
	 * 当所有事件处理器都处理过事件后(即事件处理已经达到尾声时), 本方法被调用
	 * JPRE自带的所有事件中, 都没有复写本方法. 本方法只是留给插件自定义事件用.
	 * <p>
	 * 详细过程请查看 {@link com.him188.jpre.plugin.PluginManager#callEvent(Event)}
	 */
	public void close() {

	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(cancelled=" + cancelled + ",intercepted=" + intercepted + ")";
	}
}
